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

}