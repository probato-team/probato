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
import org.probato.entity.model.Video;
import org.probato.entity.type.DimensionMode;
import org.probato.entity.type.Quality;
import org.probato.entity.type.Screen;
import org.probato.test.util.IgnoreIfWorkflow;

@IgnoreIfWorkflow
@DisplayName("Test -> ScreenRecorder")
class ScreenRecorderTest {

	@BeforeEach
	void setup() {

		Configuration.getInstance(getClass());
		var directory = new File("/probato/temp");
		if (!directory.exists()) {
			directory.mkdirs();
		}
	}

	@Test
	@DisplayName("Should record successfully")
	void shouldRecordSuccessfully() throws AWTException {

		var video = new Video();
		video.setFrameRate(100.0);
		video.setQuality(Quality.HIGH);

		var dimension = new Dimension();
		dimension.setHeight(1);
		dimension.setWidth(1);
		dimension.setMode(DimensionMode.CUSTOM);

		var screen = new ScreenRecorder("/probato/temp/test-screen-record.mp4", Screen.PRINCIPAL, video, dimension);
		screen.startCapture();
		screen.stopCapture();

		assertTrue(Boolean.TRUE);
	}

	@Test
	@DisplayName("Should validate output file")
	void shouldValidateOutputFile() {

		var video = new Video();
		video.setFrameRate(100.0);
		video.setQuality(Quality.HIGH);

		var dimension = new Dimension();
		dimension.setHeight(1);
		dimension.setWidth(1);
		dimension.setMode(DimensionMode.CUSTOM);

		var exception = assertThrows(IllegalArgumentException.class,
				() -> new ScreenRecorder(null, Screen.PRINCIPAL, video, dimension));

		assertEquals("could not open: null", exception.getMessage());
	}

}