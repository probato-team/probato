package org.probato.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.AWTException;
import java.io.File;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.probato.core.loader.Configuration;
import org.probato.entity.model.Dimension;
import org.probato.entity.type.DimensionMode;
import org.probato.entity.type.Screen;
import org.probato.exception.ExecutionException;
import org.probato.test.util.IgnoreIfWorkflow;

@IgnoreIfWorkflow
@DisplayName("Test -> Screenshot")
class ScreenshotTest {

	@BeforeEach
	void setup() {

		Configuration.getInstance(getClass());
		var directory = new File("/probato/temp");
		if (!directory.exists()) {
			directory.mkdirs();
		}
	}

	@Test
	@DisplayName("Should print successfully")
	void shouldPrintSuccessfully() throws AWTException {

		var dimension = new Dimension();
		dimension.setHeight(1);
		dimension.setWidth(1);
		dimension.setMode(DimensionMode.CUSTOM);

		var directory = new File("/probato/temp");
		if (!directory.exists()) {
			directory.mkdir();
		}

		var screen = new Screenshot("/probato/temp/test-screen-record.jpg", Screen.PRINCIPAL, dimension);
		screen.print();

		assertTrue(Boolean.TRUE);
	}

	@Test
	@DisplayName("Should validate print output file")
	void shouldValidateoutputFilePrint() throws AWTException {

		var dimension = new Dimension();
		dimension.setHeight(1);
		dimension.setWidth(1);
		dimension.setMode(DimensionMode.CUSTOM);

		var directory = new File("/probato/temp");
		if (!directory.exists()) {
			directory.mkdir();
		}

		var screen = new Screenshot(null, Screen.PRINCIPAL, dimension);
		var exception = assertThrows(ExecutionException.class, screen::print);

		assertEquals("An error occurred while trying to screenshot the execution: null", exception.getMessage());
	}
}