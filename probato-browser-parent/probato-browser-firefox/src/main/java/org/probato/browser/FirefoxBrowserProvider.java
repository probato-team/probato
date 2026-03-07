package org.probato.browser;

import org.probato.type.BrowserType;

/**
 * Firefox browser provider implementation.
 *
 * <p>
 * This provider supports Firefox browser sessions and dynamically selects
 * the underlying automation framework (e.g. Selenium or Playwright).
 * </p>
 */
public final class FirefoxBrowserProvider implements BrowserProvider {

	@Override
	public BrowserType getType() {
		return BrowserType.FIREFOX;
	}

	/**
	 * Creates a Firefox browser session based on the current execution configuration.
	 *
	 * @throws IllegalStateException if no suitable automation engine is available
	 */
	@Override
	public BrowserSession createSession(BrowserSessionData data) {
		return FirefoxSessionFactory.create(data);
	}

}