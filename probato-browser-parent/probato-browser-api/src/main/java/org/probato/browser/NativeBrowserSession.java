package org.probato.browser;

/**
 * Specialized browser session that exposes the native automation driver.
 *
 * <p>
 * This interface should be used only by advanced consumers that need direct
 * access to the underlying automation engine.
 * </p>
 *
 * @param <T> native driver type
 */
public interface NativeBrowserSession<T> extends BrowserSession {

	/**
     * Returns the native automation driver instance.
     *
     * @return native driver
     */
	T driver();

}