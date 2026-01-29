package org.probato.model;

import org.probato.type.DimensionMode;

/**
 * Represents browser window dimension settings.
 *
 * <p>The dimension may operate in different modes, such as fullscreen,
 * maximized, or custom size.</p>
 *
 * <p>When the mode is {@link DimensionMode#CUSTOM}, both width and height
 * must be explicitly provided.</p>
 */
public class Dimension {

	private Integer width;
	private Integer height;
	private DimensionMode mode;

	public Dimension() {}

	public Dimension(Integer width, Integer height, DimensionMode mode) {
		this();
		this.width = width;
		this.height = height;
		this.mode = mode;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public DimensionMode getMode() {
		return mode;
	}

	public void setMode(DimensionMode mode) {
		this.mode = mode;
	}

}