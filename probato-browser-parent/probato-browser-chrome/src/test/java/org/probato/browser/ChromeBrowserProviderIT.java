package org.probato.browser;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.WebDriver;
import org.probato.model.Browser;
import org.probato.model.Delay;
import org.probato.model.Dimension;
import org.probato.type.BrowserType;
import org.probato.type.DimensionMode;
import org.probato.type.Screen;

import com.microsoft.playwright.Page;

@DisplayName("IT - Chrome browser provider")
class ChromeBrowserProviderIT {

	@SuppressWarnings("unchecked")
	@ParameterizedTest
	@MethodSource("getParams")
	@DisplayName("Should run browser with selenium engine successfully")
	void shouldRunBrowserSeleniumEngineSuccessfully(BrowserSessionData data) {

		var provider = new ChromeBrowserProvider();

		assertAll("Validate provider",
				() -> assertNotNull(provider),
				() -> assertEquals(BrowserType.CHROME, provider.getType()),
				() -> assertEquals(BrowserType.CHROME.description(), provider.getType().description()));

        var session = provider.createSession(data);

        assertAll("Validate session",
        		() -> assertNotNull(session),
        		() -> assertNotNull(session.description()),
        		() -> assertNotNull(session.version()));

        session.run();
        session.run();

		var navigation = (NativeBrowserSession<WebDriver>) session;

		assertAll("Validate navigation",
				() -> assertNotNull(navigation.driver()),
				() -> assertNotNull(session.description()),
				() -> assertNotNull(session.version()));

        session.destroy();
	}

	@SuppressWarnings("unchecked")
	@ParameterizedTest
	@MethodSource("getParams")
	@DisplayName("Should run browser with playwright engine successfully")
	void shouldRunBrowserPlaywrightEngineSuccessfully(BrowserSessionData data) {

		var provider = new ChromeBrowserProvider();

        assertAll("Validate provider",
				() -> assertNotNull(provider),
				() -> assertEquals(BrowserType.CHROME, provider.getType()),
				() -> assertEquals(BrowserType.CHROME.description(), provider.getType().description()));

        var session = provider.createSession(data);
        assertAll("Validate session",
        		() -> assertNotNull(session),
        		() -> assertNotNull(session.description()),
        		() -> assertNotNull(session.version()));

        session.run();
        session.run();

        var navigation = (NativeBrowserSession<Page>) session;

        assertAll("Validate navigation",
				() -> assertNotNull(navigation.driver()),
				() -> assertNotNull(session.description()),
				() -> assertNotNull(session.version()));

        session.destroy();
	}

	private static Stream<Arguments> getParams() {

		var url = "https://google.com";
		var delay = new Delay(30_000, 1_000);

		return Stream.of(
				Arguments.of(
					new BrowserSessionData(
						"selenium",
						url,
						Screen.PRIMARY,
						new Browser(BrowserType.CHROME, Boolean.TRUE, new Dimension(null, null, DimensionMode.FULLSCREEN)),
						delay)
				),
				Arguments.of(
					new BrowserSessionData(
						"playwright",
						url,
						Screen.SECONDARY,
						new Browser(BrowserType.CHROME, Boolean.FALSE, new Dimension(null, null, DimensionMode.MAXIMIZED)),
						delay)
				),
				Arguments.of(
					new BrowserSessionData(
						"selenium",
						url,
						Screen.PRIMARY,
						new Browser(BrowserType.CHROME, Boolean.TRUE, new Dimension(1200, 850, DimensionMode.CUSTOM)),
						delay)
				));
	}

}