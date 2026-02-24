package org.probato.engine.procedure;

import java.util.List;
import java.util.Optional;

import org.probato.engine.ExecutionContext;
import org.probato.exception.IntegrityException;

public class ProcedureService {

	private static final String LOAD_PROCEDURE_ERROR = "An error occurred while loading the procedures in script {0}: {1}";

	private ProcedureDiscovery procedureDiscovery;
	private ProcedureBuilder procedureBuilder;
	private ProcedureExecution procedureExecution;

	private ProcedureService() {
		procedureDiscovery = ProcedureDiscovery.get();
		procedureBuilder = ProcedureBuilder.get();
		procedureExecution = ProcedureExecution.get();
	}

	public static ProcedureService get() {
		return new ProcedureService();
	}

	public List<ExecutableUnit> load(Class<?> scriptClazz) {
		try {

			var definitions = discover(scriptClazz);
			return build(definitions, scriptClazz);

		} catch (Exception ex) {
			throw new IntegrityException(
					LOAD_PROCEDURE_ERROR,
					Optional.ofNullable(scriptClazz)
						.map(Class::getSimpleName)
						.orElse(""),
					ex.getMessage());
		}
	}

	public ExecutionResult execute(ExecutionContext context, Object driver, List<ExecutableUnit> executableUnits) {
		return procedureExecution.execute(context, driver, executableUnits);
	}

	private List<ProcedureDefinition> discover(Class<?> scriptClazz) {
		return procedureDiscovery.discover(scriptClazz);
	}

	private List<ExecutableUnit> build(List<ProcedureDefinition> procedures, Class<?> scriptClazz) {
		return procedureBuilder.build(procedures, scriptClazz);
	}

}