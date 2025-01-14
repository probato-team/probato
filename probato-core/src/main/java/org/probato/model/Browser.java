package org.probato.model;

import org.probato.model.type.BrowserType;

public class Browser {

	private BrowserType type;
	private boolean headless = Boolean.FALSE;
	private Dimension dimension = new Dimension();
	
	public Browser() {}

	public Browser(BrowserBuilder builder) {
		this.type = builder.type;
		this.headless = builder.headless;
		this.dimension = builder.dimension;
	}

	public BrowserType getType() {
		return type;
	}

	public void setType(BrowserType type) {
		this.type = type;
	}

	public boolean isHeadless() {
		return headless;
	}

	public void setHeadless(boolean headless) {
		this.headless = headless;
	}

	public Dimension getDimension() {
		return dimension;
	}

	public void setDimension(Dimension dimension) {
		this.dimension = dimension;
	}
	
	public static BrowserBuilder builder() {
		return new BrowserBuilder();
	}
	
	public static class BrowserBuilder {
		
		private BrowserType type;
		private boolean headless = Boolean.FALSE;
		private Dimension dimension = new Dimension();
		
		private BrowserBuilder() {}
		
		public BrowserBuilder type(BrowserType type) {
			this.type = type;
			return this;
		}

		public BrowserBuilder headless(boolean headless) {
			this.headless = headless;
			return this;
		}

		public BrowserBuilder dimension(Dimension dimension) {
			this.dimension = dimension;
			return this;
		}
		
		public Browser build() {
			return new Browser(this);
		}
	}

}