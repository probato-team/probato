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
import org.probato.record.ScreenRecorder;
import org.probato.service.BrowserService;
import org.probato.service.RecordService;
import org.probato.type.PhaseType;

public class BrowserExecutionEngine extends ExecutionEngine {

	private final RecordService recordService;
	private final BrowserService browserService;
	private final ProcedureService procedureService;

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
	}

	@Override
	protected void init(ExecutionContext context) {
		browserSession = browserService.createSession(context.getBrowser());
		procedures = procedureService.load(context.getScriptClass());
		screenRecorder = recordService.createScreenRecord(context.getBrowser().getDimension(), executionId);
		executionBuilder = initBuilder(context);
	}

	@Override
	protected void run(ExecutionContext context) {
		try {

			browserSession.run();
			screenRecorder.startCapture();
			result = procedureService.execute(context, getDriver(), procedures);

		} finally {
			screenRecorder.stopCapture();
			browserSession.destroy();
		}
	}

	@Override
	protected void finish(ExecutionContext context) {

		var suite = buildSuite(context);
		var script = buildScript(context);
		var execution = buildExecution();

		recordService.save(suite, script, execution);
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

	private Execution buildExecution() {
		return executionBuilder
				.end(ZonedDateTime.now())
				.preconditions(result.getCollecedSteps()
						.stream()
						.filter(step -> PhaseType.PRECONDITION.equals(step.getPhase()))
						.map(step -> Step.builder()
								.sequence(step.getSequence())
								.text(step.getStepValue())
								.build())
						.collect(Collectors.toList()))
				.procedures(result.getCollecedSteps()
						.stream()
						.filter(step -> PhaseType.PROCEDURE.equals(step.getPhase()))
						.map(step -> Step.builder()
								.sequence(step.getSequence())
								.text(step.getStepValue())
								.build())
						.collect(Collectors.toList()))
				.postconditions(result.getCollecedSteps()
						.stream()
						.filter(step -> PhaseType.POSTCONDITION.equals(step.getPhase()))
						.map(step -> Step.builder()
								.sequence(step.getSequence())
								.text(step.getStepValue())
								.build())
						.collect(Collectors.toList()))
				.stepsPreconditions(result.getExecutedSteps()
						.stream()
						.filter(step -> PhaseType.PRECONDITION.equals(step.getPhase()))
						.map(step -> Step.builder()
								.sequence(step.getSequence())
								.text(step.getStepValue())
								.build())
						.collect(Collectors.toList()))
				.stepsProcedures(result.getExecutedSteps()
						.stream()
						.filter(step -> PhaseType.PROCEDURE.equals(step.getPhase()))
						.map(step -> Step.builder()
								.sequence(step.getSequence())
								.text(step.getStepValue())
								.build())
						.collect(Collectors.toList()))
				.stepsPostconditions(result.getExecutedSteps()
						.stream()
						.filter(step -> PhaseType.POSTCONDITION.equals(step.getPhase()))
						.map(step -> Step.builder()
								.sequence(step.getSequence())
								.text(step.getStepValue())
								.build())
						.collect(Collectors.toList()))
				.build();
	}

}