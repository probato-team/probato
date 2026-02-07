package org.probato.engine.procedure;

import java.util.List;
import java.util.stream.Collectors;

import org.probato.engine.ExecutionContext;
import org.probato.type.ExecutionStatus;
import org.probato.type.PhaseType;

public class ProcedureExecution {

	private ProcedureExecution() {}

	public static ProcedureExecution get() {
		return new ProcedureExecution();
	}

	public ExecutionResult execute(ExecutionContext context, Object driver, List<ExecutableUnit> executableUnits) {

		var result = new ExecutionResult();
		try {

			executePreconditions(context, driver, executableUnits, result);
			executeProcedures(context, driver, executableUnits, result);
			executePostconditions(context, driver, executableUnits, result);

			result.markFinished(ExecutionStatus.PASSED);

		} catch (Throwable ex) { // NOSONAR
			result.markFinished(ExecutionStatus.FAILED);
		}

		return result;
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

		for (var unit : getUnits(phase, units)) {

			var step = result.addStep(new StepResult(phase, unit.getOrder()));
			try {

				unit.execute(driver, context.getDatasetLine());
				step.markSuccess();

			} catch (Throwable ex) { // NOSONAR
				step.markFailure(ex);
				throw ex;
			}
		}
	}

	private List<ExecutableUnit> getUnits(PhaseType phase, List<ExecutableUnit> units) {
		return units.stream()
				.filter(unit -> phase.equals(unit.getPhase()))
				.collect(Collectors.toList());
	}

}