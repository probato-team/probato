package org.probato.engine.procedure;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.probato.type.ExecutionStatus;

public class ExecutionResult {

	private final Instant start;
	private final List<StepResult> steps;

	private Instant end;
	private ExecutionStatus status;

	public ExecutionResult() {
		start = Instant.now();
		steps = new ArrayList<>();
	}

	public StepResult addStep(StepResult step) {
		steps.add(step);
		return step;
	}

	public void markFinished(ExecutionStatus status) {
		this.status = status;
		this.end = Instant.now();
	}

	public List<StepResult> getSteps() {
		return steps;
	}

	public Duration getRuntime() {
		return Duration.between(start, end);
	}

	public ExecutionStatus getStatus() {
		return status;
	}

}