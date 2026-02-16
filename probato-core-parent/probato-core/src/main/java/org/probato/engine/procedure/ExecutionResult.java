package org.probato.engine.procedure;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.probato.type.ExecutionStatus;
import org.probato.type.PhaseType;

public class ExecutionResult {

	private final List<StepResult> collecedSteps;
	private final List<StepResult> executedSteps;

	private boolean collectMode;
	private Instant start;
	private Instant end;
	private ExecutionStatus status;
	private PhaseType currentPhase;

	public ExecutionResult() {
		collecedSteps = new ArrayList<>();
		executedSteps = new ArrayList<>();
	}

	public StepResult addCollectedStep(StepResult step) {
		collecedSteps.add(step);
		return step;
	}

	public StepResult addExecutedStep(StepResult step) {
		executedSteps.add(step);
		return step;
	}

	public void currentPhase(PhaseType currentPhase) {
		this.currentPhase = currentPhase;
	}

	public void start() {
		start = Instant.now();
	}

	public void startCollectMode() {
		collectMode = Boolean.TRUE;
	}

	public void stopCollectMode() {
		collectMode = Boolean.FALSE;
	}

	public void markFinished(ExecutionStatus status) {
		this.status = status;
		this.end = Instant.now();
	}

	public Duration getRuntime() {
		return Duration.between(start, end);
	}

	public List<StepResult> getCollecedSteps() {
		return collecedSteps;
	}

	public List<StepResult> getExecutedSteps() {
		return executedSteps;
	}

	public boolean isCollectMode() {
		return collectMode;
	}

	public Instant getStart() {
		return start;
	}

	public Instant getEnd() {
		return end;
	}

	public ExecutionStatus getStatus() {
		return status;
	}

	public PhaseType getCurrentPhase() {
		return currentPhase;
	}

}