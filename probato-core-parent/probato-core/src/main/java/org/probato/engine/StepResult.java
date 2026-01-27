package org.probato.engine;

import java.time.Instant;

import org.probato.type.PhaseType;

public class StepResult {

	private final PhaseType phase;
	private final int order;
	private final Instant start;
	private Instant end;
	private Throwable error;

	public StepResult(PhaseType phase, int order) {
		this.phase = phase;
		this.order = order;
		this.start = Instant.now();
	}

	public void markSuccess() {
		this.end = Instant.now();
	}

	public void markFailure(Throwable error) {
		this.error = error;
		this.end = Instant.now();
	}

	public boolean isFailed() {
		return error != null;
	}

	public Throwable getError() {
		return error;
	}
}