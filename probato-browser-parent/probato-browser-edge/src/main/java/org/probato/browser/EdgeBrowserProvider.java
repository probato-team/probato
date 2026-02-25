package org.probato.browser;

import org.probato.type.BrowserType;

/**
 * Edge browser provider implementation.
 *
 * <p>
 * This provider supports Edge browser sessions and dynamically selects
 * the underlying automation framework (e.g. Selenium or Playwright).
 * </p>
 */
public final class EdgeBrowserProvider implements BrowserProvider {

	@Override
	public BrowserType getType() {
		return BrowserType.EDGE;
	}

	/**
	 * Creates a Firefox browser session based on the current execution configuration.
	 *
	 * @throws IllegalStateException if no suitable automation engine is available
	 */
	@Override
	public BrowserSession createSession(BrowserSessionData data) {
	    return EdgeSessionFactory.create(data);
	}

}