package org.probato.browser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Map;
import java.util.Properties;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.WebDriver;
import org.probato.exception.IntegrityException;
import org.probato.type.BrowserType;
import org.probato.type.DimensionMode;

import com.microsoft.playwright.Page;

@DisplayName("IT - Chrome browser provider")
class ChromeBrowserProviderIT {

	private Properties originalProperties;

	@BeforeEach
    void backupSystemProperties() {
        originalProperties = (Properties) System.getProperties().clone();

        System.setProperty("execution.current", "0");
		System.setProperty("execution.target.url", "https://google.com");
		System.setProperty("execution.timeout.waiting", "10000");
		System.setProperty("execution.timeout.actionInterval", "10000");
    }

	@AfterEach
    void restoreSystemProperties() {
        System.setProperties(originalProperties);
    }

	@SuppressWarnings("unchecked")
	@ParameterizedTest
	@MethodSource("getParams")
	@DisplayName("Should run browser with selenium engine successfully")
	void shouldRunBrowserSeleniumEngineSuccessfully(Map<String, String> params) {

		System.setProperty("execution.engine", "selenium");
		params.forEach(System::setProperty);

		var provider = new ChromeBrowserProvider();

		assertNotNull(provider);
        assertEquals(BrowserType.CHROME, provider.getType());

        var session = provider.createSession();
        assertNotNull(session);
        assertNotNull(session.description());
        assertNotNull(session.version());

        session.run();
        session.run();

		var navigation = (NativeBrowserSession<WebDriver>) session;

        assertNotNull(navigation.driver());
        assertNotNull(session.description());
        assertNotNull(session.version());

        session.destroy();
	}

	@SuppressWarnings("unchecked")
	@ParameterizedTest
	@MethodSource("getParams")
	@DisplayName("Should run browser with playwright engine successfully")
	void shouldRunBrowserPlaywrightEngineSuccessfully(Map<String, String> params) {

		System.setProperty("execution.engine", "playwright");
		params.forEach(System::setProperty);

		var provider = new ChromeBrowserProvider();

		assertNotNull(provider);
        assertEquals(BrowserType.CHROME, provider.getType());

        var session = provider.createSession();
        assertNotNull(session);
        assertNotNull(session.description());
        assertNotNull(session.version());

        session.run();
        session.run();

        var navigation = (NativeBrowserSession<Page>) session;

        assertNotNull(navigation.driver());
        assertNotNull(session.description());
        assertNotNull(session.version());

        session.destroy();
	}

	@ParameterizedTest
	@MethodSource("getInvalidParams")
	@DisplayName("Should validate when required parameters are not provided.")
	void shouldValidateRequiredParams(Map<String, String> params, String expected) {

		System.setProperty("execution.engine", "playwright");
		params.forEach(System::setProperty);

		var provider = new ChromeBrowserProvider();

		assertNotNull(provider);
        assertEquals(BrowserType.CHROME, provider.getType());

       var exception = assertThrows(IntegrityException.class, provider::createSession);

       assertEquals(expected, exception.getMessage());
	}

	private static Stream<Arguments> getParams() {
		return Stream.of(
				Arguments.of(
					Map.of(
						"execution.screen", "PRIMARY",
						"browsers.[0].type", BrowserType.CHROME.toString(),
						"browsers.[0].headless", "true",
						"browsers.[0].dimension.mode", DimensionMode.FULLSCREEN.toString())
				),
				Arguments.of(
					Map.of(
						"execution.screen", "SECONDARY",
						"browsers.[0].type", BrowserType.CHROME.toString(),
						"browsers.[0].headless", "false",
						"browsers.[0].dimension.mode", DimensionMode.MAXIMIZED.toString())
				),
				Arguments.of(
					Map.of(
						"execution.screen", "OTHER",
						"browsers.[0].type", BrowserType.CHROME.toString(),
						"browsers.[0].headless", "true",
						"browsers.[0].dimension.mode", DimensionMode.CUSTOM.toString(),
						"browsers.[0].dimension.width", "1200",
						"browsers.[0].dimension.height", "850")
				));
	}

	private static Stream<Arguments> getInvalidParams() {
		return Stream.of(
				Arguments.of(
					Map.of(
						"browsers.[0].dimension.mode", DimensionMode.FULLSCREEN.toString()),
					"Property 'browsers[0].type' should be declared in 'configuration.yaml' file"
				),
				Arguments.of(
					Map.of(
						"browsers.[0].dimension.mode", "OTHER"),
					"Invalid value for DimensionMode property: OTHER"
					),
				Arguments.of(
					Map.of(
						"browsers.[0].type", BrowserType.CHROME.toString(),
						"browsers.[0].headless", "true"),
					"Property 'browsers[0].dimension.mode' should be declared in 'configuration.yaml' file"
				),
				Arguments.of(
					Map.of(
						"browsers.[0].type", "OTHER",
						"browsers.[0].headless", "true"),
					"Invalid value for BrowserType property: OTHER"
				),
				Arguments.of(
					Map.of(
						"browsers.[0].type", BrowserType.CHROME.toString(),
						"browsers.[0].headless", "true",
						"browsers.[0].dimension.mode", DimensionMode.CUSTOM.toString(),
						"browsers.[0].dimension.height", "850"),
					"Property 'browsers[0].dimension.height' and 'browsers[0].dimension.width' should be declared in 'configuration.yaml' file when 'browsers[0].dimension.mode' equals CUSTOM"
				),
				Arguments.of(
					Map.of(
						"browsers.[0].type", BrowserType.CHROME.toString(),
						"browsers.[0].headless", "true",
						"browsers.[0].dimension.mode", DimensionMode.CUSTOM.toString(),
						"browsers.[0].dimension.width", "1200"),
					"Property 'browsers[0].dimension.height' and 'browsers[0].dimension.width' should be declared in 'configuration.yaml' file when 'browsers[0].dimension.mode' equals CUSTOM"
				));
	}

}