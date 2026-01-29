package org.probato.type;

/**
 * Represents the business relevance of a script.
 */
public enum Relevance {

	VERY_LOW(2),
	LOW(4),
	MEDIUM(8),
	HIGH(16),
	VERY_HIGH(32),

	;

	private final Integer coefficient;

	private Relevance(Integer coefficient) {
		this.coefficient = coefficient;
	}

	public Integer getCoefficient() {
		return coefficient;
	}

}