package org.probato.record;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.AWTException;
import java.io.File;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.probato.model.Dimension;
import org.probato.model.Video;
import org.probato.model.type.DimensionMode;
import org.probato.model.type.Quality;
import org.probato.model.type.Screen;

@DisplayName("Test -> ScreenRecorder")
class ScreenRecorderTest {

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
		
		var directory = new File("C:/probato/temp");
		if (!directory.exists()) {
			directory.mkdir();
		}

		var screen = new ScreenRecorder("/probato/temp/test-screen-record.mp4", Screen.PRINCIPAL, video, dimension);
		screen.startCapture();
		screen.stopCapture();

		assertTrue(Boolean.TRUE);
	}
}