package org.probato.model.type;

public enum Quality {

	HIGH(1), 
	MEDIUM(2), 
	LOW(4),

	;

	private final int divisor;

	private Quality(int divisor) {
		this.divisor = divisor;
	}

	public int getDivisor() {
		return divisor;
	}

}