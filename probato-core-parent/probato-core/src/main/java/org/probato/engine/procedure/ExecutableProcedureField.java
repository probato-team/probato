package org.probato.engine.procedure;

import java.lang.reflect.Field;

import org.probato.loader.AnnotationLoader;
import org.probato.type.PhaseType;

public class ExecutableProcedureField implements ExecutableUnit {

	private final InicializerExecutionService inicializer;
	private final PhaseType phase;
	private final int order;
	private final Class<?> scriptClazz;
	private final Field field;

	public ExecutableProcedureField(PhaseType phase, int order, Class<?> scriptClazz, Object field) {
		this.inicializer = new InicializerExecutionService();
		this.phase = phase;
		this.order = order;
		this.scriptClazz = scriptClazz;
		this.field = (Field) field;
	}

	public PhaseType getPhase() {
		return phase;
	}

	public int getOrder() {
		return order;
	}

	@Override
	public void execute(Object driver, Integer datasetLine, ExecutionResult result) throws Exception {

		var type = field.getType();
		var method = AnnotationLoader.getRunMethod(type);

		var objectScript = inicializer.newInstance(field.getDeclaringClass());
		var objectProcedure = inicializer.newInstance(type);

		field.setAccessible(Boolean.TRUE); // NOSONAR
		field.set(objectScript, objectProcedure); // NOSONAR

		initializePagesObject(driver, type, objectProcedure, result);
		executeMethod(scriptClazz, datasetLine, objectProcedure, method);
	}

}