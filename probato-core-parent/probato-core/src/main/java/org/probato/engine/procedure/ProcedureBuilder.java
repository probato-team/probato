package org.probato.engine.procedure;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ProcedureBuilder {

	private ProcedureBuilder() {}

	public static ProcedureBuilder get() {
		return new ProcedureBuilder();
	}

	public List<ExecutableUnit> build(List<ProcedureDefinition> definitions, Class<?> scriptClazz) {
		return definitions.stream()
				.map(definition -> build(definition, scriptClazz))
				.sorted(Comparator.comparingInt(ExecutableUnit::getOrder))
				.collect(Collectors.toList());
	}

	private ExecutableUnit build(ProcedureDefinition definition, Class<?> scriptClazz) {
		if (definition.isMethod()) {
			return new ExecutableProcedureMethod(definition.getPhase(), definition.getOrder(), scriptClazz, definition.getMethod());
		} else {
			return new ExecutableProcedureField(definition.getPhase(), definition.getOrder(), scriptClazz, definition.getField());
		}
	}

}