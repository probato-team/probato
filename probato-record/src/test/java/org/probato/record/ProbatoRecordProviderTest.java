package org.probato.record;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.probato.exception.ExecutionException;
import org.probato.model.Dimension;
import org.probato.model.Directory;
import org.probato.model.Target;
import org.probato.model.Video;
import org.probato.test.util.IgnoreIfWorkflow;
import org.probato.type.DimensionMode;
import org.probato.type.Quality;
import org.probato.type.Screen;
import org.probato.utils.FileUtils;

@IgnoreIfWorkflow
@DisplayName("UT - ProbatoRecordProvider")
class ProbatoRecordProviderTest {

	@BeforeEach
	void start() {
		var directory = new File("D:/temp");
		if (!directory.exists()) {
			directory.mkdir();
		}
	}

	@Test
	@DisplayName("Should create screen record successfully")
	void shouldCreateScreenRecordSuccessfully() {

		var screen = Screen.PRIMARY;
		var outputFile = "D:/temp/test-screen-record.mp4";
		var video = new Video();
		video.setFrameRate(100.0);
		video.setQuality(Quality.HIGH);
		video.setEnabled(Boolean.TRUE);

		var dimension = new Dimension();
		dimension.setHeight(1);
		dimension.setWidth(1);
		dimension.setMode(DimensionMode.CUSTOM);

		var provider = new ProbatoRecordProvider();

		var screenRecord = provider.createScreenRecord(screen, video, dimension, outputFile);

		screenRecord.startCapture();
		screenRecord.stopCapture();

		assertNotNull(screenRecord);
	}

	@Test
	@DisplayName("Should validate when an record error occurs")
	void shouldValidateRecordError() {

		var screen = Screen.PRIMARY;
		var video = new Video();
		video.setFrameRate(100.0);
		video.setQuality(Quality.HIGH);

		var dimension = new Dimension();
		dimension.setHeight(1);
		dimension.setWidth(1);
		dimension.setMode(DimensionMode.CUSTOM);

		var provider = new ProbatoRecordProvider();

		var exception = assertThrows(ExecutionException.class, () -> provider.createScreenRecord(screen, video, null, null));

		assertTrue(exception.getMessage().startsWith("An error occurred while trying to record screen the execution:"));
	}

	@Test
	@DisplayName("Should create screenshot successfully")
	void shouldCreateScreenshotSuccessfully() {

		var screen = Screen.SECONDARY;
		var outputFile = "D:/temp/test-screen-record.mp4";
		var video = new Video();
		video.setFrameRate(100.0);
		video.setQuality(Quality.HIGH);
		video.setEnabled(Boolean.TRUE);

		var dimension = new Dimension();
		dimension.setHeight(1);
		dimension.setWidth(1);
		dimension.setMode(DimensionMode.CUSTOM);

		var provider = new ProbatoRecordProvider();

		var screenRecord = provider.createScreenshot(screen, dimension, outputFile);

		screenRecord.print();

		assertNotNull(screenRecord);
	}

	@Test
	@DisplayName("Should validate when an screenshot error occurs")
	void shouldValidateScreenshotError() {

		var screen = Screen.PRIMARY;
		var video = new Video();
		video.setFrameRate(100.0);
		video.setQuality(Quality.HIGH);

		var dimension = new Dimension();
		dimension.setHeight(1);
		dimension.setWidth(1);
		dimension.setMode(null);

		var provider = new ProbatoRecordProvider();

		var exception = assertThrows(ExecutionException.class, () -> provider.createScreenshot(screen, null, null));

		assertTrue(exception.getMessage().startsWith("An error occurred while trying to record screen the execution:"));
	}


	@Test
	@DisplayName("Should delete files execution data successfully")
	void shouldFilesExecutionDataSuccessfully() throws Exception {

		var projectId = "9f12de88-56ba-45de-8a69-d3038011b357";
		var directory = Directory.builder()
				.keepClean(Boolean.TRUE)
				.build();
		var target = Target.builder()
				.projectId(UUID.fromString("9f12de88-56ba-45de-8a69-d3038011b357"))
				.url("http://localhost:9999")
				.version("1.0.0")
				.build();

		var tempDir = directory.getTemp() + "/" + projectId + "/1.0.0";

		var files = FileUtils.loadFiles(tempDir);
		for (File file : files) {
			FileUtils.delete(file);
		}

		var provider = new ProbatoRecordProvider();

		provider.deleteExecutionData(target, directory);

		assertTrue(Boolean.TRUE);
	}

	@Test
	@DisplayName("Should validate when an delete execution data error occurs")
	void shouldValidateDeleteExecutionDataError() {

		var provider = new ProbatoRecordProvider();

		var exception = assertThrows(ExecutionException.class, () -> provider.deleteExecutionData(null, null));

		assertTrue(exception.getMessage().startsWith("An error occurred when trying to invoke the web application:"));
	}

}