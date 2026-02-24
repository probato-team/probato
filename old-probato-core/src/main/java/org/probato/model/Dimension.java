package org.probato.model;

import org.probato.model.type.DimensionMode;

public class Dimension {

	private Integer width;
	private Integer height;
	private DimensionMode mode = DimensionMode.MAXIMIZED;
	
	public Dimension() {}

	public Dimension(DimensionBuilder builder) {
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
	
	public boolean isCustom() {
		return DimensionMode.CUSTOM.equals(this.mode);
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