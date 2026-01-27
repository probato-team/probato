package org.probato.engine;

import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.probato.type.ExecutionStatus;
import org.probato.type.PhaseType;

public class ExecutionEngine {

	public ExecutionRecord execute(ExecutionContext context) {

		var record = new ExecutionRecord();

		try {

			var scriptInstance = context.getScriptClass().getDeclaredConstructor().newInstance();

			List<ExecutableUnit> units = ScriptDiscovery.discover(context.getScriptClass()).stream()
					.map(def -> buildUnit(def, scriptInstance))
					.sorted(Comparator.comparingInt(ExecutableUnit::getOrder)).collect(Collectors.toList());

			if (executePhase(units, PhaseType.PRECONDITION, record, true)) {
				record.markFinished(ExecutionStatus.SKIPPED);
				return record;
			}

			if (executePhase(units, PhaseType.PROCEDURE, record, false)) {
				record.markFinished(ExecutionStatus.FAILED);
				return record;
			}

			if (executePhase(units, PhaseType.POSTCONDITION, record, false)) {
				record.markFinished(ExecutionStatus.FAILED);
				return record;
			}

			record.markFinished(ExecutionStatus.PASSED);
			return record;

		} catch (Exception e) {
			record.markFinished(ExecutionStatus.FAILED);
			return record;
		}
	}

	private boolean executePhase(
			List<ExecutableUnit> units,
			PhaseType phase,
			ExecutionRecord record,
			boolean skipOnFailure) {

		for (ExecutableUnit unit : units) {

			if (unit.getPhase() != phase) {
				continue;
			}

			var step = new StepResult(phase, unit.getOrder());
			try {
				unit.execute();
				step.markSuccess();
			} catch (Exception e) {
				step.markFailure(e);
				record.addStep(step);
				return true;
			}
			record.addStep(step);
		}
		return false;
	}

	private ExecutableUnit buildUnit(ExecutableDefinition def, Object scriptInstance) {

		try {

			if (def.isMethod()) {
				return new ExecutableUnit(def.getPhase(), def.getOrder(), scriptInstance, def.getMethod());
			}

			def.getField().setAccessible(true);
			Object fieldInstance = def.getField().getType().getDeclaredConstructor().newInstance();

			def.getField().set(scriptInstance, fieldInstance);

			Method runMethod = findRunMethod(fieldInstance.getClass());

			return new ExecutableUnit(def.getPhase(), def.getOrder(), fieldInstance, runMethod);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private Method findRunMethod(Class<?> clazz) {
		for (Method method : clazz.getDeclaredMethods()) {
			if (method.isAnnotationPresent(org.probato.api.Run.class)) {
				return method;
			}
		}
		throw new IllegalStateException("Classe " + clazz.getName() + " não possui método @Run");
	}

}