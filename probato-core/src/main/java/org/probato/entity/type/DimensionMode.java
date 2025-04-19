package org.probato.entity.type;

public enum DimensionMode {

	FULLSCREEN("Fullscreen"), 
	MAXIMIZED("Maximized"), 
	CUSTOM("Custom"),

	;

	private String description;

	private DimensionMode(String description) {
		this.description = description;
	}

	public String description() {
		return this.description;
	}

}