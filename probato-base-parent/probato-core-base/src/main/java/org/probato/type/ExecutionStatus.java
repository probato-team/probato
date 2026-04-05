package org.probato.type;

/**
 * Represents the execution status.
 */
public enum ExecutionStatus {

	PASSED,
	FAILED,
	ERROR,
	SKIPPED,

	;

	public Evaluation getEvaluation() {
		switch (this) {
			case PASSED:
				return Evaluation.SUCCESS;
			case FAILED:
				return Evaluation.FAILURE;
			case ERROR:
				return Evaluation.ERROR;
			case SKIPPED:
				return Evaluation.IMPEDITIVE;
			default:
				throw new IllegalStateException("Unexpected status: " + this);
		}
	}

}