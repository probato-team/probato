package org.probato.browser;

import org.probato.type.BrowserType;

/**
 * Chrome browser provider implementation.
 *
 * <p>
 * This provider supports Chrome browser sessions and dynamically selects
 * the underlying automation framework (e.g. Selenium or Playwright).
 * </p>
 */
public final class ChromeBrowserProvider implements BrowserProvider {

	@Override
	public BrowserType getType() {
		return BrowserType.CHROME;
	}

	/**
	 * Creates a Chrome browser session based on the current execution configuration.
	 *
	 * @throws IllegalStateException if no suitable automation engine is available
	 */
	@Override
	public BrowserSession createSession(BrowserSessionData data) {
	    return ChromeSessionFactory.create(data);
	}

}