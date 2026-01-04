package org.probato.browser;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.probato.exception.IntegrityException;
import org.probato.type.BrowserType;
import org.probato.type.DimensionMode;

@DisplayName("UT - Chrome session factory")
class ChromeSessionFactoryTest {

	private static final String ENGINE_PROPERTY = "execution.engine";

	@BeforeEach
	void setup() {

		System.setProperty("execution.current", "0");
		System.setProperty("execution.screen", "PRIMARY");
		System.setProperty("browsers.[0].type", BrowserType.CHROME.toString());
		System.setProperty("browsers.[0].headless", "true");
		System.setProperty("browsers.[0].dimension.mode", DimensionMode.FULLSCREEN.toString());
	}

	@AfterEach
	void restoreSystemProperty() {
		System.clearProperty(ENGINE_PROPERTY);
	}

	@Test
	void shouldCreatePlaywrightSessionWhenEngineIsPlaywright() {

		System.setProperty(ENGINE_PROPERTY, "playwright");

		var session = ChromeSessionFactory.create();

		assertNotNull(session);
		assertTrue(session instanceof PlaywrightChromeSession,
				"Expected PlaywrightChromeSession when execution.engine=playwright");
	}

	@Test
	void shouldCreateSeleniumSessionWhenEngineIsSelenium() {

		System.setProperty(ENGINE_PROPERTY, "selenium");

		var session = ChromeSessionFactory.create();

		assertNotNull(session);
		assertTrue(session instanceof SeleniumChromeSession,
				"Expected SeleniumChromeSession when execution.engine=selenium");
	}

	@Test
	void shouldCreateSeleniumSessionWhenEngineIsNotDefined() {
		System.clearProperty(ENGINE_PROPERTY);

		var session = ChromeSessionFactory.create();

		assertNotNull(session);
		assertTrue(session instanceof SeleniumChromeSession,
				"Expected SeleniumChromeSession when execution.engine is not defined");
	}

	@Test
	void shouldFailWhenEngineIsInvalid() {

		System.setProperty(ENGINE_PROPERTY, "invalid-engine");

		var exception = assertThrows(IntegrityException.class, ChromeSessionFactory::create);

		assertTrue(exception.getMessage().contains("execution.engine"),
				"Error message should reference execution.engine property");
	}

}