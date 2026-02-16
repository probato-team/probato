package org.probato.record;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.AWTException;
import java.io.File;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.probato.exception.ExecutionException;
import org.probato.model.Dimension;
import org.probato.type.DimensionMode;
import org.probato.type.Screen;

@DisplayName("UT - Screenshot")
class ScreenshotTest {

	@BeforeEach
	void start() {
		var directory = new File("D:/temp");
		if (!directory.exists()) {
			directory.mkdir();
		}
	}

	@Test
	@DisplayName("Should print successfully")
	void shouldPrintSuccessfully() throws AWTException {

		var dimension = new Dimension();
		dimension.setHeight(1);
		dimension.setWidth(1);
		dimension.setMode(DimensionMode.CUSTOM);

		var screen = new Screenshot("D:/temp/test-screen-record.jpg", Screen.PRIMARY, dimension);
		screen.print();

		assertTrue(Boolean.TRUE);
	}

	@Test
	@DisplayName("Should validate when data error")
	void shouldValidateData() throws AWTException {

		var dimension = new Dimension();
		dimension.setHeight(1);
		dimension.setWidth(1);
		dimension.setMode(DimensionMode.CUSTOM);

		var screen = new Screenshot(null, Screen.PRIMARY, dimension);
		var exception = assertThrows(ExecutionException.class, screen::print);

		assertTrue(exception.getMessage().startsWith("An error occurred while trying to screenshot the execution:"));
	}

}