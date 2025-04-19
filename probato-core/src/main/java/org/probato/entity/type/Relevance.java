package org.probato.entity.type;

public enum Relevance {

	VERY_LOW(2), 
	LOW(4), 
	AVERAGE(8), 
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