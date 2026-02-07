package org.probato.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.probato.exception.IntegrityException;
import org.probato.model.Browser;
import org.probato.model.Dimension;
import org.probato.type.BrowserType;
import org.probato.type.DimensionMode;

@DisplayName("UT - BrowserService")
class BrowserServiceIT {

	@Test
	@DisplayName("Should execute browser service successfully")
	void shouldExecuteBrowserSuccessfully() {

		var browser = Browser.builder()
				.type(BrowserType.CHROME)
				.headless(Boolean.TRUE)
				.dimension(Dimension.builder()
						.width(1200)
						.height(800)
						.mode(DimensionMode.CUSTOM)
						.build())
				.build();

		var service = BrowserService.get();

		var browserSession = service.createSession(browser);
		browserSession.run();
		browserSession.destroy();

		assertAll("Validate data",
				() -> assertEquals("0.0.0", browserSession.version()),
				() -> assertEquals("Fake Chrome", browserSession.description()));
	}

	@Test
	@DisplayName("Should validate when implement not found")
	void shouldValidateImplementNotFound() {

		var browser = Browser.builder()
				.type(BrowserType.FIREFOX)
				.headless(Boolean.TRUE)
				.dimension(Dimension.builder()
						.width(1200)
						.height(800)
						.mode(DimensionMode.CUSTOM)
						.build())
				.build();

		var service = BrowserService.get();

		var exception = assertThrows(IntegrityException.class, () -> service.createSession(browser));

		assertEquals("Browser provider implementation not found for Firefox type", exception.getMessage());
	}

}