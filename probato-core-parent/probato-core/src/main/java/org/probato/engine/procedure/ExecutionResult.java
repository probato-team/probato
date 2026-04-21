package org.probato.engine.procedure;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.probato.exception.ExecutionException;
import org.probato.type.ExecutionStatus;
import org.probato.type.PhaseType;

public class ExecutionResult {

	private final List<StepResult> collecedSteps;
	private final List<StepResult> executedSteps;

	private int collecedStepCount = 0;
	private int executedStepCount = 0;
	private boolean collectMode;
	private Instant start;
	private Instant end;
	private ExecutionStatus status;
	private PhaseType currentPhase;
	private ExecutionException motive;

	public ExecutionResult() {
		status = ExecutionStatus.SKIPPED;
		collecedSteps = new ArrayList<>();
		executedSteps = new ArrayList<>();
	}

	public StepResult addCollectedStep(StepResult step) {
		step.sequence(++collecedStepCount);
		collecedSteps.add(step);
		return step;
	}

	public StepResult addExecutedStep(StepResult step) {
		step.sequence(++executedStepCount);
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

	public void markFinished(ExecutionStatus status, ExecutionException motive) {
		markFinished(status);
		this.motive = motive;
	}

	public void markFinished(ExecutionStatus status, Exception motive) {
		markFinished(status);
		this.motive = new ExecutionException(motive.getMessage());
	}

	public void markFinished(ExecutionStatus status) {
		this.status = status;
		this.end = Instant.now();
	}

	public boolean hasSuccess() {
		return ExecutionStatus.PASSED.equals(getStatus());
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

	public ExecutionStatus getStatus() {
		return status;
	}

	public PhaseType getCurrentPhase() {
		return currentPhase;
	}

	public ExecutionException getMotive() {
		return motive;
	}

	public String getMotiveMessage() {
		return Optional.ofNullable(motive)
				.map(Throwable::toString)
				.orElse(null);
	}

}