package org.probato.model;

import org.probato.type.BrowserType;

/**
 * Represents a browser configuration used by Probato.
 *
 * <p>This class encapsulates the browser type, headless mode
 * and window dimension settings.</p>
 *
 * <p><strong>Note:</strong> The default constructor reads configuration
 * values from JVM system properties and validates them on instantiation.</p>
 */
public class Browser {

	private BrowserType type;
	private boolean headless;
	private Dimension dimension;

	public Browser() {}

	public Browser(BrowserType type, boolean headless, Dimension dimension) {
		this();
		this.type = type;
		this.headless = headless;
		this.dimension = dimension;
	}

	public Browser(BrowserBuilder builder) {
		this();
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
		private boolean headless;
		private Dimension dimension;

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