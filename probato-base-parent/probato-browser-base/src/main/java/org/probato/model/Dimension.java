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

	public Dimension(DimensionBuilder builder) {
		this();
		this.width = builder.width;
		this.height = builder.height;
		this.mode = builder.mode;
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

	public static DimensionBuilder builder() {
		return new DimensionBuilder();
	}

	public static class DimensionBuilder {

		private Integer width;
		private Integer height;
		private DimensionMode mode;

		private DimensionBuilder() {}

		public DimensionBuilder width(Integer width) {
			this.width = width;
			return this;
		}

		public DimensionBuilder height(Integer height) {
			this.height = height;
			return this;
		}

		public DimensionBuilder mode(DimensionMode mode) {
			this.mode = mode;
			return this;
		}

		public Dimension build() {
			return new Dimension(this);
		}
	}

}