package org.probato.engine;

import java.time.ZonedDateTime;
import java.util.List;

import org.opentest4j.AssertionFailedError;
import org.probato.api.Postcondition;
import org.probato.api.Precondition;
import org.probato.api.Procedure;
import org.probato.datasource.DatasourceService;
import org.probato.engine.Execution.ExecutionBuilder;
import org.probato.engine.Script.ScriptBuilder;
import org.probato.engine.Suite.SuiteBuilder;
import org.probato.exception.ImpeditiveException;
import org.probato.loader.AnnotationLoader;
import org.probato.model.type.Evaluation;

public class DesktopScriptExecutionService implements ScriptExecutor {

	private static final String IMPEDITIVE_MSG = "An error occurred while running preconditions: {0}";
	
	private List<DatasourceService> datasourcesService;
	private ProcedureExecutionService procedureExecutionService;

	private final Class<?> suiteClazz;
	private final Class<?> scriptClazz;
	private final Integer datasetLine;
	private ExecutionService executionService;
	private List<Object> preconditions;
	private List<Object> procedures;
	private List<Object> postconditions;
	private SuiteBuilder suiteBuilder;
	private ScriptBuilder scriptBuilder;
	private ExecutionBuilder executionBuilder;

	public DesktopScriptExecutionService(Class<?> suiteClazz, Class<?> scriptClazz, Integer datasetLine) {
		
		this.suiteClazz = suiteClazz;
		this.scriptClazz = scriptClazz;
		this.datasetLine = datasetLine;
		
		this.datasourcesService = DatasourceService.getInstance();
		this.procedureExecutionService = new ProcedureExecutionService();
		this.executionService = ExecutionService.getInstance(scriptClazz);
	}

	@Override
	public void run() {
		init();
		execute();
		finish();
	}

	private void init() {
		
		ExecutionContextHolder.clean();
		initProcedures();
		suiteBuilder = initSuiteBuilder();
		scriptBuilder = initScriptBuilder();
		executionBuilder = initExecutionBuilder();
	}

	private void execute() {
		try {
			
			executionBuilder
				.start(ZonedDateTime.now());

			collectUseCaseData();
			loadSQL();
			executeUseCase();

			executionBuilder.evaluation(Evaluation.SUCCESS);
			
		} catch (Throwable ex) {

			executionBuilder
				.evaluation(getEvaluation(ex))
				.motive(ex);
			
		} finally {
			executionService.endRecording();
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

	private void finish() {

		executionBuilder.end(ZonedDateTime.now());

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
				.sql(this.scriptClazz);
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
			procedureExecutionService.execute(null, scriptClazz, datasetLine, preconditions);
		} catch (Exception ex) {
			throw new ImpeditiveException(IMPEDITIVE_MSG, ex.getMessage());
		} finally {
			executionBuilder.preconditions(ExecutionContextHolder.getActions());
		}
	}

	private void executeProcedures() throws Throwable {
		try {
			procedureExecutionService.execute(null, scriptClazz, datasetLine, procedures);
		} finally {
			executionBuilder.procedures(ExecutionContextHolder.getActions());
		}
	}

	private void executePostconditions() throws Throwable {
		try {
			procedureExecutionService.execute(null, scriptClazz, datasetLine, postconditions);
		} finally {
			executionBuilder.postconditions(ExecutionContextHolder.getActions());
		}
	}

	private void executeSteps(List<Object> procedures) throws Throwable {
		procedureExecutionService.execute(null, scriptClazz, datasetLine, procedures);
	}
}