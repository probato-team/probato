package org.probato.browser;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.probato.exception.IntegrityException;
import org.probato.model.Browser;
import org.probato.model.Delay;
import org.probato.model.Dimension;
import org.probato.type.BrowserType;
import org.probato.type.DimensionMode;
import org.probato.type.Screen;

@DisplayName("UT - Chrome session factory")
class ChromeSessionFactoryTest {

	@Test
	void shouldCreatePlaywrightSessionWhenEngineIsPlaywright() {

		var url = "https://google.com";
		var delay = new Delay(30_000, 1_000);
		var data = new BrowserSessionData(
				new Browser(BrowserType.CHROME, Boolean.TRUE, new Dimension(null, null, DimensionMode.FULLSCREEN)),
				"playwright",
				url,
				Screen.PRIMARY,
				delay);

		var session = ChromeSessionFactory.create(data);

		assertNotNull(session);
		assertTrue(session instanceof PlaywrightChromeSession,
				"Expected PlaywrightChromeSession when execution.engine=playwright");
	}

	@Test
	void shouldCreateSeleniumSessionWhenEngineIsSelenium() {

		var url = "https://google.com";
		var delay = new Delay(30_000, 1_000);
		var data = new BrowserSessionData(
				new Browser(BrowserType.CHROME, Boolean.TRUE, new Dimension(null, null, DimensionMode.FULLSCREEN)),
				"selenium",
				url,
				Screen.PRIMARY,
				delay);

		var session = ChromeSessionFactory.create(data);

		assertNotNull(session);
		assertTrue(session instanceof SeleniumChromeSession,
				"Expected SeleniumChromeSession when execution.engine=selenium");
	}

	@Test
	void shouldCreateSeleniumSessionWhenEngineIsNotDefined() {

		var url = "https://google.com";
		var delay = new Delay(30_000, 1_000);
		var data = new BrowserSessionData(
				new Browser(BrowserType.CHROME, Boolean.TRUE, new Dimension(null, null, DimensionMode.FULLSCREEN)),
				null,
				url,
				Screen.PRIMARY,
				delay);

		var session = ChromeSessionFactory.create(data);

		assertNotNull(session);
		assertTrue(session instanceof SeleniumChromeSession,
				"Expected SeleniumChromeSession when execution.engine is not defined");
	}

	@Test
	void shouldFailWhenEngineIsInvalid() {

		var url = "https://google.com";
		var delay = new Delay(30_000, 1_000);
		var data = new BrowserSessionData(
				new Browser(BrowserType.CHROME, Boolean.TRUE, new Dimension(null, null, DimensionMode.FULLSCREEN)),
				"invalid-engine",
				url,
				Screen.PRIMARY,
				delay);

		var exception = assertThrows(IntegrityException.class, () -> ChromeSessionFactory.create(data));

		assertTrue(exception.getMessage().contains("execution.engine"),
				"Error message should reference execution.engine property");
	}

}