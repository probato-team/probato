package org.probato.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.probato.core.loader.Configuration;
import org.probato.entity.model.Dimension;
import org.probato.entity.type.DimensionMode;
import org.probato.exception.ExecutionException;
import org.probato.test.util.IgnoreIfWorkflow;

@IgnoreIfWorkflow
@DisplayName("Test -> RecordServiceExecutionData")
class RecordServiceExecutionDataTest {

	private RecordServiceExecutionData service;

	@BeforeEach
	void setup() {

		Configuration.getInstance(getClass());
		var directory = new File("/probato/temp");
		if (!directory.exists()) {
			directory.mkdirs();
		}

		service = new RecordServiceExecutionData();
	}

	@Test
	@DisplayName("Should record successfully")
	void shouldRecordSuccessfully() {

		var dimension = new Dimension();
		dimension.setHeight(1);
		dimension.setWidth(1);
		dimension.setMode(DimensionMode.CUSTOM);

		var outputFileMp4 = "/probato/temp/test-screen-record.mp4";

		service.start(outputFileMp4, dimension);
		service.stop();

		assertTrue(Boolean.TRUE);
	}

	@Test
	@DisplayName("Should print successfully")
	void shouldPrintSuccessfully() {

		var dimension = new Dimension();
		dimension.setHeight(1);
		dimension.setWidth(1);
		dimension.setMode(DimensionMode.CUSTOM);

		var outputFileJpg = "/probato/temp/test-screen-record.jpg";

		service.screenshot(outputFileJpg, dimension);

		assertTrue(Boolean.TRUE);
	}

	@Test
	@DisplayName("Should validate record output file")
	void shouldValidateOutputRecordFile() {

		var dimension = new Dimension();
		dimension.setHeight(1);
		dimension.setWidth(1);
		dimension.setMode(DimensionMode.CUSTOM);

		var exception = assertThrows(ExecutionException.class,
				() -> service.start(null, dimension));

		assertEquals("An error occurred while trying to record screen the execution: could not open: null", exception.getMessage());
	}

	@Test
	@DisplayName("Should validate print output file")
	void shouldValidateOutputPrintFile() {

		var dimension = new Dimension();
		dimension.setHeight(1);
		dimension.setWidth(1);
		dimension.setMode(DimensionMode.CUSTOM);

		var exception = assertThrows(ExecutionException.class,
				() -> service.screenshot(null, dimension));

		assertEquals("An error occurred while trying to record screen the execution: An error occurred while trying to screenshot the execution: null", exception.getMessage());
	}

}