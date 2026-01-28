package org.probato.browser;

import java.util.Optional;

import org.probato.exception.IntegrityException;

/**
 * Factory responsible for creating Chrome browser sessions.
 */
final class ChromeSessionFactory {

	private ChromeSessionFactory() {}

	/**
	 * Creates a Chrome {@link BrowserSession} based on the execution engine.
	 *
	 * <p>
	 * The execution engine is selected using the {@code execution.engine}
	 * system property.
	 * </p>
	 *
	 * <ul>
	 *   <li>{@code playwright} → Playwright-based session</li>
	 *   <li>{@code selenium} (default) → Selenium-based session</li>
	 * </ul>
	 *
	 * @throws IntegrityException if the configured execution engine is not supported
	 */
	public static BrowserSession create(BrowserSessionData data) {
		switch (resolveEngine(data)) {
		case "playwright":
			return new PlaywrightChromeSession(data);
		case "selenium":
			return new SeleniumChromeSession(data);
		default:
			throw new IntegrityException("Invalid value ''{0}'' for property ''execution.engine''.", resolveEngine(data));
		}
	}

	private static String resolveEngine(BrowserSessionData data) {
		return Optional.ofNullable(data.getEngine())
				.map(String::toLowerCase)
				.orElse("selenium");
	}

}