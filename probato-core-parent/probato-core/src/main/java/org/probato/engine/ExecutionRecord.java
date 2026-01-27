package org.probato.engine;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.probato.type.ExecutionStatus;

public class ExecutionRecord {

	private final Instant start = Instant.now();
	private Instant end;
	private final List<StepResult> steps = new ArrayList<>();
	private ExecutionStatus status;

	public void addStep(StepResult step) {
		steps.add(step);
	}

	public void markFinished(ExecutionStatus status) {
		this.status = status;
		this.end = Instant.now();
	}

	public Duration getRuntime() {
		return Duration.between(start, end);
	}

	public ExecutionStatus getStatus() {
		return status;
	}

}