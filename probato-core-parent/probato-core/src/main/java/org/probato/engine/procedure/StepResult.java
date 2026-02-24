package org.probato.engine.procedure;

import java.lang.reflect.Method;
import java.time.Instant;
import java.util.Objects;
import java.util.Optional;

import org.probato.type.PhaseType;

public class StepResult {

	private Integer sequence;
	private Class<?> clazz;
	private Method method;
	private String actionValue;
	private String stepValue;
	private Instant start;
	private Instant end;
	private Throwable error;
	private PhaseType phase;

	public StepResult(Method method, PhaseType phase) {
		this.method = method;
		this.phase = phase;
		this.clazz = Optional.ofNullable(method)
				.map(Method::getDeclaringClass)
				.orElse(null);
	}

	public void start() {
		this.start = Instant.now();
	}

	public void stop() {
		this.end = Instant.now();
	}

	public Throwable error(Throwable error) {
		this.error = error;
		this.end = Instant.now();
		return error;
	}

	public void sequence(int sequence) {
		this.sequence = sequence;
	}

	public void actionValue(String actionValue) {
		this.actionValue = actionValue;
	}

	public void stepValue(String stepValue) {
		this.stepValue = stepValue;
	}

	public Boolean hasSuccess() {
		return Objects.isNull(error);
	}

	public Integer getSequence() {
		return sequence;
	}

	public String getClazz() {
		return Optional.ofNullable(clazz)
				.map(Class::getName)
				.orElse(null);
	}

	public String getMethod() {
		return Optional.ofNullable(method)
				.map(Method::getName)
				.orElse(null);
	}

	public String getActionValue() {
		return actionValue;
	}

	public String getStepValue() {
		return stepValue;
	}

	public Instant getStart() {
		return start;
	}

	public Instant getEnd() {
		return end;
	}

	public String getError() {
		return Optional.ofNullable(error)
				.map(Throwable::toString)
				.orElse(null);
	}

	public PhaseType getPhase() {
		return phase;
	}

}