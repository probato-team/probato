package org.probato.engine;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.probato.type.PhaseType;

public class ExecutableUnit {

	private final PhaseType phase;
	private final int order;
	private final Object target;
	private final Method method;

	public ExecutableUnit(PhaseType phase, int order, Object target, Method method) {
		this.phase = phase;
		this.order = order;
		this.target = target;
		this.method = method;
		this.method.setAccessible(Boolean.TRUE);
	}

	public PhaseType getPhase() {
		return phase;
	}

	public int getOrder() {
		return order;
	}

	public void execute() throws IllegalAccessException, InvocationTargetException {
		method.invoke(target);
	}
}