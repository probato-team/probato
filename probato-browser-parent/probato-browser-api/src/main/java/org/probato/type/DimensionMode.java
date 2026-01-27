package org.probato.type;

import org.probato.exception.IntegrityException;

/**
 * Defines how the browser window dimension should be handled
 * during initialization or runtime.
 */
public enum DimensionMode {

	/**
	 * The browser window will be displayed in fullscreen mode.
	 */
	FULLSCREEN("Fullscreen"),

	/**
	 * The browser window will be maximized to occupy the available screen area.
	 */
	MAXIMIZED("Maximized"),

	/**
	 * The browser window will use a custom width and height defined externally.
	 */
	CUSTOM("Custom"),

	;

	private final String description;

	private DimensionMode(String description) {
		this.description = description;
	}

	/**
	 * Returns a human-readable description of this dimension mode.
	 *
	 * <p>This description is intended for logging or diagnostic purposes
	 * and should not be used for localization-sensitive UI elements.</p>
	 *
	 * @return a textual description of the dimension mode
	 */
	public String description() {
		return this.description;
	}

	public static DimensionMode fromString(String value) {
		try {
			return valueOf(value.toUpperCase());
		} catch (Exception ex) {
			throw new IntegrityException("Invalid value for DimensionMode property: {0}", value);
		}
	}

}