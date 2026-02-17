package org.probato.engine.procedure;

import java.util.List;
import java.util.stream.Collectors;

import org.probato.engine.ExecutionContext;
import org.probato.exception.ExecutionException;
import org.probato.type.ExecutionStatus;
import org.probato.type.PhaseType;

public class ProcedureExecution {

	private static final String MSG_COLLECT_DATA_ERROR = "An error occurred while trying to collect test data: {0}";
	private static final String MSG_EXECUTE_ERROR = "An error occurred while running test: {0}";

	private ProcedureExecution() {}

	public static ProcedureExecution get() {
		return new ProcedureExecution();
	}

	public ExecutionResult execute(ExecutionContext context, Object driver, List<ExecutableUnit> executableUnits) {

		var result = new ExecutionResult();
		result.start();

		try {
			collectData(context, driver, executableUnits, result);
		} catch (Throwable ex) { // NOSONAR
			result.markFinished(
					ExecutionStatus.ERROR,
					new ExecutionException(MSG_COLLECT_DATA_ERROR, ex.getCause().toString()));

			return result;
		}

		try {
			execute(context, driver, executableUnits, result);
			result.markFinished(ExecutionStatus.PASSED);
		} catch (Throwable ex) { // NOSONAR
			result.markFinished(
					ExecutionStatus.FAILED,
					new ExecutionException(MSG_EXECUTE_ERROR, ex.getCause().toString()));
		}

		return result;
	}

	private void collectData(ExecutionContext context, Object driver, List<ExecutableUnit> executableUnits, ExecutionResult result) throws Throwable {

		result.startCollectMode();

		executePreconditions(context, driver, executableUnits, result);
		executeProcedures(context, driver, executableUnits, result);
		executePostconditions(context, driver, executableUnits, result);
	}

	private void execute(ExecutionContext context, Object driver, List<ExecutableUnit> executableUnits, ExecutionResult result) throws Throwable {

		result.stopCollectMode();

		executePreconditions(context, driver, executableUnits, result);
		executeProcedures(context, driver, executableUnits, result);
		executePostconditions(context, driver, executableUnits, result);
	}

	private void executePreconditions(ExecutionContext context, Object driver, List<ExecutableUnit> executableUnits, ExecutionResult result) throws Throwable {
		executePhase(driver, context, executableUnits, PhaseType.PRECONDITION, result);
	}

	private void executeProcedures(ExecutionContext context, Object driver, List<ExecutableUnit> executableUnits, ExecutionResult result) throws Throwable {
		executePhase(driver, context, executableUnits, PhaseType.PROCEDURE, result);
	}

	private void executePostconditions(ExecutionContext context, Object driver, List<ExecutableUnit> executableUnits, ExecutionResult result) throws Throwable {
		executePhase(driver, context, executableUnits, PhaseType.POSTCONDITION, result);
	}

	private void executePhase(
			Object driver,
			ExecutionContext context,
			List<ExecutableUnit> units,
			PhaseType phase,
			ExecutionResult result) throws Throwable { // NOSONAR

		result.currentPhase(phase);
		for (var unit : getUnits(phase, units)) {
			unit.execute(driver, context.getDatasetLine(), result);
		}
	}

	private List<ExecutableUnit> getUnits(PhaseType phase, List<ExecutableUnit> units) {
		return units.stream()
				.filter(unit -> phase.equals(unit.getPhase()))
				.collect(Collectors.toList());
	}

}