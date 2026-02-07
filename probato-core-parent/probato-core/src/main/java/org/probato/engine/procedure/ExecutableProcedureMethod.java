package org.probato.engine.procedure;

import java.lang.reflect.Method;

import org.probato.type.PhaseType;

public class ExecutableProcedureMethod implements ExecutableUnit {

	private final InicializerExecutionService inicializer;
	private final PhaseType phase;
	private final int order;
	private final Class<?> scriptClazz;
	private final Method method;

	public ExecutableProcedureMethod(PhaseType phase, int order, Class<?> scriptClazz, Object method) {
		this.inicializer = new InicializerExecutionService();
		this.phase = phase;
		this.order = order;
		this.scriptClazz = scriptClazz;
		this.method = (Method) method;
	}

	@Override
	public void execute(Object driver, Integer datasetLine) throws Exception {
		var object = inicializer.newInstance(method.getDeclaringClass());
		initializePagesObject(driver, method.getDeclaringClass(), object);
		executeMethod(scriptClazz, datasetLine, object, method);
	}

	@Override
	public PhaseType getPhase() {
		return phase;
	}

	public int getOrder() {
		return order;
	}

}