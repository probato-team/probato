package org.probato.model.type;

public enum Screen {

	PRINCIPAL(0), 
	SECONDARY(1), 

	;

	private final Integer position;

	private Screen(Integer position) {
		this.position = position;
	}

	public Integer position() {
		return this.position;
	}

}