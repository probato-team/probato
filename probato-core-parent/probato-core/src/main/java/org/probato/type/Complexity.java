package org.probato.type;

/**
 * Represents the technical complexity of a script.
 */
public enum Complexity {

	VERY_LOW(2),
	LOW(4),
	MEDIUM(8),
	HIGH(16),
	VERY_HIGH(32),

	;

	private final Integer coefficient;

	private Complexity(Integer coefficient) {
		this.coefficient = coefficient;
	}

	public Integer getCoefficient() {
		return this.coefficient;
	}

}