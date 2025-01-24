package org.probato.record;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.AWTException;
import java.io.File;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.probato.model.Dimension;
import org.probato.model.type.DimensionMode;
import org.probato.model.type.Screen;

@DisplayName("Test -> Screenshot")
class ScreenshotTest {

	@Disabled
	@Test
	@DisplayName("Should print successfully")
	void shouldPrintSuccessfully() throws AWTException {

		var dimension = new Dimension();
		dimension.setHeight(1);
		dimension.setWidth(1);
		dimension.setMode(DimensionMode.CUSTOM);
		
		var directory = new File("C:/probato/temp");
		if (!directory.exists()) {
			directory.mkdir();
		}

		var screen = new Screenshot("/probato/temp/test-screen-record.jpg", Screen.PRINCIPAL, dimension);
		screen.print();

		assertTrue(Boolean.TRUE);
	}
}