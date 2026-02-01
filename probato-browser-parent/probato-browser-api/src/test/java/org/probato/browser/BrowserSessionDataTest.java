package org.probato.browser;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.probato.model.Browser;
import org.probato.model.Delay;
import org.probato.model.Dimension;
import org.probato.type.BrowserType;
import org.probato.type.DimensionMode;
import org.probato.type.Screen;

class BrowserSessionDataTest {

	@Test
	@DisplayName("Should create object successfully")
	void shouldConverterValueSuccessfully() {

		var dimension = new Dimension();
		dimension.setHeight(1200);
		dimension.setWidth(800);
		dimension.setMode(DimensionMode.CUSTOM);

		var browser = new Browser();
		browser.setType(BrowserType.CHROME);
		browser.setHeadless(Boolean.TRUE);
		browser.setDimension(dimension);

		var delay = new Delay(30_000, 1_000);
		delay.setWaitingTimeout(30_000);
		delay.setActionInterval(1_000);

		var data = new BrowserSessionData("selenium", "http://google.com", Screen.PRIMARY, browser, delay);

		assertAll("Validate value",
				() -> assertEquals("selenium", data.getEngine()),
				() -> assertEquals("http://google.com", data.getUrl()),
				() -> assertEquals(Screen.PRIMARY, data.getScreen()),
				() -> assertEquals(browser, data.getBrowser()),
				() -> assertEquals(delay, data.getDelay()),
				() -> assertEquals(delay.getWaitingTimeout(), data.getDelay().getWaitingTimeout()),
				() -> assertEquals(delay.getActionInterval(), data.getDelay().getActionInterval()));
	}

}