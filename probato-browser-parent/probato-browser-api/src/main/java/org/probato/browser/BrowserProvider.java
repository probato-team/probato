package org.probato.browser;

import org.probato.type.BrowserType;

/**
 * <p>
 * There must be at most one {@code BrowserProvider} implementation
 * per {@link BrowserType}.
 * </p>
 */
public interface BrowserProvider {

	/**
     * Returns the browser type supported by this provider.
     *
     * @return the supported {@link BrowserType}
     */
	BrowserType getType();

	/**
     * Creates a new browser session based on the given browser configuration.
     *
     * <p>
     * The returned session encapsulates the lifecycle of the underlying
     * browser automation engine.
     * </p>
     *
     * @return a new {@link BrowserSession}
     * @throws IntegrityException if the browser configuration is invalid
     */
	BrowserSession createSession();

}