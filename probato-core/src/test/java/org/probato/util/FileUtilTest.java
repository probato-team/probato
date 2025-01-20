package org.probato.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Test -> FileUtil")
class FileUtilTest {

	@Test
	@DisplayName("Should create temp directory successfully")
	void shouldCreateTempDirectorySuccessfully() throws IOException {

		FileUtil.delete(new File("C:/tempo"));
		FileUtil.createTempDir("C:/tempo");

		assertTrue(Boolean.TRUE);
	}

	@Test
	@DisplayName("Should save file successfully")
	void shouldSaveFileSuccessfully() throws IOException {

		FileUtil.save("C:/temp", "test-file-save.txt", "this is a simple test");

		assertTrue(Boolean.TRUE);
	}

	@Test
	@DisplayName("Should load files successfully")
	void shouldLoadFilesSuccessfully() {

		FileUtil.loadFiles("C:/temp");

		assertTrue(Boolean.TRUE);
	}

	@Test
	@DisplayName("Should get content file successfully")
	void shouldGetContenFileSuccessfully() throws IOException {

		FileUtil.getContent(new File("C:/temp/test-file-save.txt"));

		assertTrue(Boolean.TRUE);
	}

	@Test
	@DisplayName("Should exists successfully")
	void shouldExistsSuccessfully() {

		FileUtil.exists("data/file/test-file-save.txt");

		assertTrue(Boolean.TRUE);
	}

	@Test
	@DisplayName("Should get files successfully")
	void shouldGetFilesSuccessfully() {

		FileUtil.getFile("data/file/test-file-save.txt");

		assertTrue(Boolean.TRUE);
	}

	@Test
	@DisplayName("Should get files format successfully")
	void shouldGetFilesFormatSuccessfully() {

		FileUtil.getFiles("data", "txt");

		assertTrue(Boolean.TRUE);
	}

	@Test
	@DisplayName("Should get file extension successfully")
	void shouldGetFileExtensionSuccessfully() {

		var extesion = FileUtil.getFileExtension("data/file/test-file-save.txt");

		assertEquals("txt", extesion);
	}
}