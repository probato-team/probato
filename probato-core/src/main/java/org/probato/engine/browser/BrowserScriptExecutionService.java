package org.probato.engine.browser;

import java.time.ZonedDateTime;
import java.util.List;

import org.opentest4j.AssertionFailedError;
import org.probato.api.Postcondition;
import org.probato.api.Precondition;
import org.probato.api.Procedure;
import org.probato.browser.BrowserService;
import org.probato.core.loader.AnnotationLoader;
import org.probato.datasource.DatasourceService;
import org.probato.engine.ScriptExecutor;
import org.probato.engine.execution.ExecutionContextHolder;
import org.probato.engine.execution.ExecutionService;
import org.probato.engine.execution.ProcedureExecutionService;
import org.probato.engine.execution.builder.Execution;
import org.probato.engine.execution.builder.Execution.ExecutionBuilder;
import org.probato.engine.execution.builder.Script;
import org.probato.engine.execution.builder.Script.ScriptBuilder;
import org.probato.engine.execution.builder.Suite;
import org.probato.engine.execution.builder.Suite.SuiteBuilder;
import org.probato.entity.model.Browser;
import org.probato.entity.type.Evaluation;
import org.probato.exception.ImpeditiveException;

public class BrowserScriptExecutionService extends ScriptExecutor {

	private static final String IMPEDITIVE_MSG = "An error occurred while running preconditions: {0}";
	
	private List<DatasourceService> datasourcesService;
	private ProcedureExecutionService procedureExecutionService;

	private final Class<?> suiteClazz;
	private final Class<?> scriptClazz;
	private final Browser browser;
	private final Integer datasetLine;
	
	private BrowserService browserService;
	private ExecutionService executionService;
	private List<Object> preconditions;
	private List<Object> procedures;
	private List<Object> postconditions;
	private SuiteBuilder suiteBuilder;
	private ScriptBuilder scriptBuilder;
	private ExecutionBuilder executionBuilder;

	public BrowserScriptExecutionService(Class<?> suiteClazz, Class<?> scriptClazz, Browser browser, Integer datasetLine) {
		
		this.suiteClazz = suiteClazz;
		this.scriptClazz = scriptClazz;
		this.browser = browser;
		this.datasetLine = datasetLine;
		
		this.datasourcesService = DatasourceService.getInstance();
		this.procedureExecutionService = new ProcedureExecutionService();
		this.browserService = BrowserService.getInstance(browser);
		this.executionService = ExecutionService.getInstance(scriptClazz);
	}

	@Override
	protected void init() {
		
		ExecutionContextHolder.clean();
		initProcedures();
		suiteBuilder = initSuiteBuilder();
		scriptBuilder = initScriptBuilder();
		executionBuilder = initExecutionBuilder();		
	}

	@Override
	protected void execute() {
		try {
			
			browserService.run();

			executionBuilder
					.browser(this.browser, browserService.getBrowserDescription(), browserService.getBrowserVersion());
			
			executionBuilder
				.video(executionService.startRecording(browser.getDimension()));

			collectUseCaseData();
			loadSQL();
			executeUseCase();

			executionBuilder.evaluation(Evaluation.SUCCESS);
			
		} catch (Throwable ex) {

			executionBuilder
				.evaluation(getEvaluation(ex))
				.motive(ex)
				.image(executionService.captureScreen(browser.getDimension()));
			
		} finally {
			executionService.endRecording();
		}
	}

	@Override
	protected void finish() {

		executionBuilder.end(ZonedDateTime.now());
		browserService.destroy();

		var suite = suiteBuilder.build();
		var script = scriptBuilder.build();
		var execution = executionBuilder.build();
		executionService.save(suite, script, execution);
		
		if (Evaluation.FAILURE.equals(execution.getEvaluation())) {
			throw new AssertionFailedError(execution.getMotive());
		} else if (Evaluation.ERROR.equals(execution.getEvaluation())) {
			throw new RuntimeException(execution.getMotive());
		} else if (Evaluation.IMPEDITIVE.equals(execution.getEvaluation())) {
			throw new AssertionFailedError(execution.getMotive());
		}
	}

	private Evaluation getEvaluation(Throwable ex) {

		Evaluation evaluation = null;
		if (ex instanceof ImpeditiveException) {
			evaluation = Evaluation.IMPEDITIVE;
		} else if (ex instanceof Error) {
			evaluation = Evaluation.FAILURE;
		} else if (ex instanceof RuntimeException) {
			evaluation = Evaluation.FAILURE;
		} else {
			evaluation = Evaluation.ERROR;
		}

		return evaluation;
	}
	
	private SuiteBuilder initSuiteBuilder() {
		return Suite.builder().clazz(suiteClazz);
	}

	private ScriptBuilder initScriptBuilder() {
		return Script.builder()
				.clazz(suiteClazz, scriptClazz);
	}

	private ExecutionBuilder initExecutionBuilder() {
		return Execution.builder()
				.project()
				.env()
				.id(executionService.getExecutionId())
				.suite(this.suiteClazz)
				.script(this.scriptClazz)
				.dataset(this.scriptClazz, this.datasetLine)
				.sql(this.suiteClazz)
				.sql(this.scriptClazz)
				.start(ZonedDateTime.now());
	}
	
	private void initProcedures() {
		preconditions = AnnotationLoader.getProceduresScript(this.scriptClazz, Precondition.class);
		procedures = AnnotationLoader.getProceduresScript(this.scriptClazz, Procedure.class);
		postconditions = AnnotationLoader.getProceduresScript(this.scriptClazz, Postcondition.class);
	}

	private void collectUseCaseData() throws Throwable  {
		ExecutionContextHolder.onCollectionMode();
		collectStepsPreconditions();
		collectStepsProcedures();
		collectStepsPostconditions();
		ExecutionContextHolder.offCollectionMode();
	}

	private void collectStepsPreconditions() throws Throwable {
		executeSteps(preconditions);
		scriptBuilder.preconditions(ExecutionContextHolder.getActions());
		executionBuilder.stepsPreconditions(ExecutionContextHolder.getSteps());
	}

	private void collectStepsProcedures() throws Throwable {
		executeSteps(procedures);
		scriptBuilder.procedures(ExecutionContextHolder.getActions());
		executionBuilder.stepsProcedures(ExecutionContextHolder.getSteps());
	}

	private void collectStepsPostconditions() throws Throwable {
		executeSteps(postconditions);
		scriptBuilder.postconditions(ExecutionContextHolder.getActions());
		executionBuilder.stepsPostconditions(ExecutionContextHolder.getSteps());
	}

	private void loadSQL() {
		datasourcesService.stream()
			.forEach(service -> {
				service.run(suiteClazz);
				service.run(scriptClazz);
			});
	}

	private void executeUseCase() throws Throwable {
		ExecutionContextHolder.offCollectionMode();
		executePreconditions();
		executeProcedures();
		executePostconditions();
	}

	private void executePreconditions() throws Throwable {
		try {
			procedureExecutionService.execute(browserService.driver(), scriptClazz, datasetLine, preconditions);
		} catch (Exception ex) {
			throw new ImpeditiveException(IMPEDITIVE_MSG, ex.getMessage());
		} finally {
			executionBuilder.preconditions(ExecutionContextHolder.getActions());
		}
	}

	private void executeProcedures() throws Throwable {
		try {
			procedureExecutionService.execute(browserService.driver(), scriptClazz, datasetLine, procedures);
		} finally {
			executionBuilder.procedures(ExecutionContextHolder.getActions());
		}
	}

	private void executePostconditions() throws Throwable {
		try {
			procedureExecutionService.execute(browserService.driver(), scriptClazz, datasetLine, postconditions);
		} finally {
			executionBuilder.postconditions(ExecutionContextHolder.getActions());
		}
	}

	private void executeSteps(List<Object> procedures) throws Throwable {
		procedureExecutionService.execute(browserService.driver(), scriptClazz, datasetLine, procedures);
	}

}