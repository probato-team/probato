package org.probato.engine.procedure;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.probato.type.PhaseType;

public class ProcedureDefinition {

	private final PhaseType phase;
	private final Method method;
	private final Field field;
	private final int order;

	private ProcedureDefinition(PhaseType phase, Method method, Field field, int order) {
		this.phase = phase;
		this.method = method;
		this.field = field;
		this.order = order;
	}

	public static ProcedureDefinition forMethod(PhaseType phase, Method method, int order) {
		return new ProcedureDefinition(phase, method, null, order);
	}

	public static ProcedureDefinition forField(PhaseType phase, Field field, int order) {
		return new ProcedureDefinition(phase, null, field, order);
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