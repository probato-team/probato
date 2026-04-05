package org.probato.record;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.probato.model.Directory;
import org.probato.model.Target;
import org.probato.utils.FileUtils;

@DisplayName("UT - DeleteExecutionData")
class DeleteExecutionDataTest {

	private DeleteExecutionData deleteExecutionData;

	@BeforeEach
	void setup() throws IOException {

		deleteExecutionData = new DeleteExecutionData();

		var projectId = "9f12de88-56ba-45de-8a69-d3038011b357";
		var directory = new Directory();

		var tempDir = directory.getTemp() + "/" + projectId + "/1.0.0";

		var files = FileUtils.loadFiles(tempDir);
		for (File file : files) {
			FileUtils.delete(file);
		}

		FileUtils.createTempDir(tempDir);
		FileUtils.save(tempDir, "000-UC01.json", "{}");
		FileUtils.save(tempDir, "001-UC01TC01.json", "{}");
		FileUtils.save(tempDir, "002-e5543501-ffcd-43e4-948b-9dc8576d5c80.json", "{}");
	}

	@Test
	@DisplayName("Should integration successfully")
	void shouldIntegrationSuccessfully() {

		var directory = Directory.builder()
				.keepClean(Boolean.TRUE)
				.build();
		var target = Target.builder()
				.projectId(UUID.fromString("9f12de88-56ba-45de-8a69-d3038011b357"))
				.url("http://localhost:9999")
				.version("1.0.0")
				.build();

		deleteExecutionData.execute(target, directory);

		assertTrue(Boolean.TRUE);
	}

}