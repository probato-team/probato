package org.probato.engine;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.probato.browser.BrowserSession;
import org.probato.browser.NativeBrowserSession;
import org.probato.engine.builder.Execution;
import org.probato.engine.builder.Execution.ExecutionBuilder;
import org.probato.engine.builder.Script;
import org.probato.engine.builder.Step;
import org.probato.engine.builder.Suite;
import org.probato.engine.procedure.ExecutableUnit;
import org.probato.engine.procedure.ExecutionResult;
import org.probato.engine.procedure.ProcedureService;
import org.probato.exception.ExecutionException;
import org.probato.record.ScreenRecorder;
import org.probato.service.BrowserService;
import org.probato.service.NoSqlService;
import org.probato.service.RecordService;
import org.probato.service.SqlService;
import org.probato.type.ExecutionStatus;
import org.probato.type.PhaseType;

public class BrowserExecutionEngine extends ExecutionEngine {

	private final RecordService recordService;
	private final BrowserService browserService;
	private final ProcedureService procedureService;
	private final SqlService sqlService;
	private final NoSqlService noSqlService;

	private UUID executionId;
	private ExecutionResult result;
	private ScreenRecorder screenRecorder;
	private BrowserSession browserSession;
	private List<ExecutableUnit> procedures;
	private ExecutionBuilder executionBuilder;

	public BrowserExecutionEngine() {
		executionId = UUID.randomUUID();
		recordService = RecordService.get();
		browserService = BrowserService.get();
		procedureService = ProcedureService.get();
		sqlService = SqlService.get();
		noSqlService = NoSqlService.get();
	}

	@Override
	protected void start(ExecutionContext context) {
		browserSession = browserService.createSession(context.getBrowser());
		procedures = procedureService.load(context.getScriptClass());
		screenRecorder = recordService.createScreenRecord(context.getBrowser().getDimension(), executionId);
		executionBuilder = initBuilder(context);
	}

	@Override
	protected void run(ExecutionContext context) {
		try {

			result = collectData(context);
			loadSQL(context);

			browserSession.run();

			screenRecorder.startCapture();

			executeScript(context);

		} catch (Exception ex) {

			result.markFinished(ExecutionStatus.ERROR, ex);

		} finally {

			screenRecorder.stopCapture();
			browserSession.destroy();
		}
	}

	@Override
	protected void finish(ExecutionContext context) {

		var suite = buildSuite(context);
		var script = buildScript(context);
		var execution = buildExecution(context);

		recordService.save(suite, script, execution);

		if (!result.hasSuccess()) {
			throw result.getMotive();
		}
	}

	private Object getDriver() {
		return ((NativeBrowserSession<?>) browserSession).driver();
	}

	private ExecutionBuilder initBuilder(ExecutionContext context) {
		return Execution.builder()
				.project()
				.env()
				.id(executionId)
				.suite(context.getSuiteClass())
				.script(context.getScriptClass())
				.dataset(context.getScriptClass(), context.getDatasetLine())
				.sql(context.getSuiteClass())
				.sql(context.getScriptClass())
				.start(ZonedDateTime.now());
	}

	private Suite buildSuite(ExecutionContext context) {
		return Suite.builder()
				.clazz(context.getSuiteClass())
				.build();
	}

	private ExecutionResult collectData(ExecutionContext context) {
		return procedureService.collectData(context, procedures);
	}

	private void executeScript(ExecutionContext context) {
		procedureService.execute(context, getDriver(), procedures, result);
	}

	private void loadSQL(ExecutionContext context) {
		try {
			sqlService.run(context.getSuiteClass());
			sqlService.run(context.getScriptClass());
		} catch (Exception ex) {
			result.markFinished(ExecutionStatus.ERROR, new ExecutionException(ex.getMessage()));
			throw ex;
		}
	}

	private Script buildScript(ExecutionContext context) {
		return Script.builder()
				.clazz(context.getSuiteClass(), context.getScriptClass())
				.preconditions(result.getCollecedSteps()
						.stream()
						.filter(step -> PhaseType.PRECONDITION.equals(step.getPhase()))
						.map(step -> Step.builder()
								.sequence(step.getSequence())
								.text(step.getActionValue())
								.build())
						.collect(Collectors.toList()))
				.procedures(result.getCollecedSteps()
						.stream()
						.filter(step -> PhaseType.PROCEDURE.equals(step.getPhase()))
						.map(step -> Step.builder()
								.sequence(step.getSequence())
								.text(step.getActionValue())
								.build())
						.collect(Collectors.toList()))
				.postconditions(result.getCollecedSteps()
						.stream()
						.filter(step -> PhaseType.POSTCONDITION.equals(step.getPhase()))
						.map(step -> Step.builder()
								.sequence(step.getSequence())
								.text(step.getActionValue())
								.build())
						.collect(Collectors.toList()))
				.build();
	}

	private Execution buildExecution(ExecutionContext context) {
		return executionBuilder
				.evaluation(result.getStatus().getEvaluation())
				.end(ZonedDateTime.now())
				.sql(context.getSuiteClass())
				.sql(context.getScriptClass())
				.video(executionId)// TODO: Check exists screen recording
				// .image(executionId)// TODO: Check exists screenshot
				.preconditions(result.getCollecedSteps()
						.stream()
						.filter(step -> PhaseType.PRECONDITION.equals(step.getPhase()))
						.map(step -> Step.builder()
								.sequence(step.getSequence())
								.text(step.getStepValue())
								.start(step.getStart())
								.end(step.getEnd())
								.clazz(step.getClazz())
								.method(step.getMethod())
								.error(step.getError())
								.build())
						.collect(Collectors.toList()))
				.procedures(result.getCollecedSteps()
						.stream()
						.filter(step -> PhaseType.PROCEDURE.equals(step.getPhase()))
						.map(step -> Step.builder()
								.sequence(step.getSequence())
								.text(step.getStepValue())
								.start(step.getStart())
								.end(step.getEnd())
								.clazz(step.getClazz())
								.method(step.getMethod())
								.error(step.getError())
								.build())
						.collect(Collectors.toList()))
				.postconditions(result.getCollecedSteps()
						.stream()
						.filter(step -> PhaseType.POSTCONDITION.equals(step.getPhase()))
						.map(step -> Step.builder()
								.sequence(step.getSequence())
								.text(step.getStepValue())
								.start(step.getStart())
								.end(step.getEnd())
								.clazz(step.getClazz())
								.method(step.getMethod())
								.error(step.getError())
								.build())
						.collect(Collectors.toList()))
				.stepsPreconditions(result.getExecutedSteps()
						.stream()
						.filter(step -> PhaseType.PRECONDITION.equals(step.getPhase()))
						.map(step -> Step.builder()
								.sequence(step.getSequence())
								.text(step.getStepValue())
								.start(step.getStart())
								.end(step.getEnd())
								.clazz(step.getClazz())
								.method(step.getMethod())
								.error(step.getError())
								.build())
						.collect(Collectors.toList()))
				.stepsProcedures(result.getExecutedSteps()
						.stream()
						.filter(step -> PhaseType.PROCEDURE.equals(step.getPhase()))
						.map(step -> Step.builder()
								.sequence(step.getSequence())
								.text(step.getStepValue())
								.start(step.getStart())
								.end(step.getEnd())
								.clazz(step.getClazz())
								.method(step.getMethod())
								.error(step.getError())
								.build())
						.collect(Collectors.toList()))
				.stepsPostconditions(result.getExecutedSteps()
						.stream()
						.filter(step -> PhaseType.POSTCONDITION.equals(step.getPhase()))
						.map(step -> Step.builder()
								.sequence(step.getSequence())
								.text(step.getStepValue())
								.start(step.getStart())
								.end(step.getEnd())
								.clazz(step.getClazz())
								.method(step.getMethod())
								.error(step.getError())
								.build())
						.collect(Collectors.toList()))
				.motive(result.getMotiveMessage())
				.build();
	}

}