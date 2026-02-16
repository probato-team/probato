package org.probato.engine.procedure;

import java.lang.reflect.Method;
import java.time.Instant;
import java.util.Objects;

import org.probato.type.PhaseType;

public class StepResult {

	private int sequence;
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
	}

	public void start() {
		this.start = Instant.now();
	}

	public void stop() {
		this.end = Instant.now();
	}

	public void error(Throwable error) {
		this.error = error;
		this.end = Instant.now();
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

	public void phase(PhaseType phase) {
		this.phase = phase;
	}

	public Boolean hasSuccess() {
		return Objects.isNull(error);
	}

	public int getSequence() {
		return sequence;
	}

	public Method getMethod() {
		return method;
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

	public Throwable getError() {
		return error;
	}

	public PhaseType getPhase() {
		return phase;
	}

}