package org.probato.browser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.probato.exception.IntegrityException;
import org.probato.loader.Configuration;
import org.probato.model.Browser;
import org.probato.model.Browser.BrowserBuilder;
import org.probato.model.Dimension;
import org.probato.model.type.BrowserType;
import org.probato.model.type.DimensionMode;
import org.probato.test.util.IgnoreIfWorkflow;

@DisplayName("Test - BrowserService")
class BrowserServiceTest {

	@IgnoreIfWorkflow
	@ParameterizedTest
	@MethodSource("getBrowsers")
	@DisplayName("Should execute test successfully")
	void shouldExecuteSuccessfully(Browser browser) throws Throwable {

		Configuration.getInstance(getClass());
		
		var service = BrowserService.getInstance(browser);
		service.run();
		service.destroy();

		assertTrue(Boolean.TRUE);
	}
	
	@Test
	@DisplayName("Should not load successfully")
	void shouldNotLoadSuccessfully() {

		var dimension = new Dimension();
		dimension.setHeight(1);
		dimension.setWidth(1);
		dimension.setMode(DimensionMode.CUSTOM);

		var browser = new Browser();
		browser.setType(BrowserType.FAKE);
		browser.setDimension(dimension);

		Configuration.getInstance(getClass());

		var exception = assertThrows(IntegrityException.class,
				() -> BrowserService.getInstance(browser));

		assertNotNull(exception);
		assertEquals("Browser implementation not found: FAKE", exception.getMessage());
	}

	private static Stream<Arguments> getBrowsers() {
		return Stream.of(
				Arguments.of(getBrowser().build()),
				Arguments.of(getBrowser().type(BrowserType.FIREFOX).build()),
				Arguments.of(getBrowser().type(BrowserType.EDGE).build()));
	}

	public static BrowserBuilder getBrowser() {
		return Browser.builder()
				.dimension(Dimension.builder()
						.mode(DimensionMode.MAXIMIZED)
						.build())
				.headless(Boolean.TRUE)
				.type(BrowserType.CHROME);
	}

}