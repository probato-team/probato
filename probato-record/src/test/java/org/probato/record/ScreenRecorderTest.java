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
import org.probato.model.Video;
import org.probato.test.util.IgnoreIfWorkflow;
import org.probato.type.DimensionMode;
import org.probato.type.Quality;
import org.probato.type.Screen;

@IgnoreIfWorkflow
@DisplayName("UT - ScreenRecorder")
class ScreenRecorderTest {

	@BeforeEach
	void start() {
		var directory = new File("D:/temp");
		if (!directory.exists()) {
			directory.mkdir();
		}
	}

	@Test
	@DisplayName("Should record successfully")
	void shouldRecordSuccessfully() throws AWTException {

		var video = new Video();
		video.setEnabled(Boolean.TRUE);
		video.setFrameRate(100.0);
		video.setQuality(Quality.HIGH);

		var dimension = new Dimension();
		dimension.setHeight(1);
		dimension.setWidth(1);
		dimension.setMode(DimensionMode.CUSTOM);

		var screen = new ScreenRecorder("D:/temp/test-screen-record.mp4", Screen.PRIMARY, video, dimension);
		screen.startCapture();
		screen.stopCapture();

		assertTrue(Boolean.TRUE);
	}

	@Test
	@DisplayName("Should validate when data error")
	void shouldValidateData() throws AWTException {

		var video = new Video();
		video.setEnabled(Boolean.TRUE);
		video.setFrameRate(100.0);
		video.setQuality(Quality.HIGH);

		var dimension = new Dimension();
		dimension.setHeight(1);
		dimension.setWidth(1);
		dimension.setMode(DimensionMode.CUSTOM);

		var screen = new ScreenRecorder("D:/temp/directory-not-found/test-screen-record.mp4", Screen.PRIMARY, video, dimension);
		var exception = assertThrows(ExecutionException.class, screen::startCapture);

		assertTrue(exception.getMessage().startsWith("An error occurred while trying to record screen the execution:"));
	}

}