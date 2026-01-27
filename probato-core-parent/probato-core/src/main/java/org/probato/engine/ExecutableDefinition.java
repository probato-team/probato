package org.probato.engine;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.probato.type.PhaseType;

public class ExecutableDefinition {

	private final PhaseType phase;
	private final Method method;
	private final Field field;
	private final int order;

	private ExecutableDefinition(PhaseType phase, Method method, Field field, int order) {
		this.phase = phase;
		this.method = method;
		this.field = field;
		this.order = order;
	}

	public static ExecutableDefinition forMethod(PhaseType phase, Method method, int order) {
		return new ExecutableDefinition(phase, method, null, order);
	}

	public static ExecutableDefinition forField(PhaseType phase, Field field, int order) {
		return new ExecutableDefinition(phase, null, field, order);
	}

	public PhaseType getPhase() {
		return phase;
	}

	public Method getMethod() {
		return method;
	}

	public Field getField() {
		return field;
	}

	public int getOrder() {
		return order;
	}

	public boolean isMethod() {
		return method != null;
	}
}