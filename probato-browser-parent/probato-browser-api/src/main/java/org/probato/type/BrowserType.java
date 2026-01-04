package org.probato.type;

import org.probato.exception.IntegrityException;

/**
 * Enumerates the browser types supported by Probato browser implementations.
 *
 * <p>This enum is used by the Probato Browser API to indicate which concrete
 * browser engine should be instantiated or controlled.</p>
 */
public enum BrowserType {

	/**
	 * Google Chrome browser.
	 */
	CHROME,

	/**
	 * Mozilla Firefox browser.
	 */
	FIREFOX,

	/**
	 * Microsoft Edge browser.
	 */
	EDGE,

	/**
	 * Apple Safari browser.
	 */
	SAFARI,

	/**
	 * Opera browser.
	 */
	OPERA,

	/**
	 * @deprecated Internet Explorer is deprecated and should not be used in new implementations.
	 */
	@Deprecated
	IE,

	;

	/**
	 * Parses a browser type from its textual representation.
	 *
	 * <p>The provided value must match exactly one of the constants defined
	 * in {@link BrowserType}, ignoring case sensitivity.</p>
	 *
	 * <p>If the value does not correspond to any supported browser type,
	 * an {@link IntegrityException} is thrown with a descriptive message.</p>
	 *
	 * @param value the textual representation of the browser type
	 * @return the corresponding {@link BrowserType}
	 * @throws IntegrityException if the value is {@code null}, empty,
	 *         or does not match any {@link BrowserType} constant
	 */
	public static BrowserType fromString(String value) {
		try {
			return valueOf(value.toUpperCase());
		} catch (Exception ex) {
			throw new IntegrityException("Invalid value for BrowserType property: {0}", value);
		}
	}

}