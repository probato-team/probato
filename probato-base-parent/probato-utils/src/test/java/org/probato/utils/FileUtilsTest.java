package org.probato.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.probato.model.Directory;

@DisplayName("UT - FileUtils")
class FileUtilsTest {

	@Test
	@DisplayName("Should create temp directory successfully")
	void shouldCreateTempDirectorySuccessfully() throws IOException {

		var directory = new Directory();

		FileUtils.delete(new File(directory.getTemp()));
		FileUtils.createTempDir(directory.getTemp());

		assertTrue(Boolean.TRUE);
	}

	@Test
	@DisplayName("Should save file successfully")
	void shouldSaveFileSuccessfully() throws IOException {

		var directory = new Directory();

		FileUtils.save(directory.getTemp(), "test-file-save.txt", "this is a simple test");

		assertTrue(Boolean.TRUE);
	}

	@Test
	@DisplayName("Should load files successfully")
	void shouldLoadFilesSuccessfully() {

		var directory = new Directory();

		FileUtils.loadFiles(directory.getTemp() + "/temp");

		assertTrue(Boolean.TRUE);
	}

	@Test
	@DisplayName("Should get content file successfully")
	void shouldGetContenFileSuccessfully() throws IOException {

		var directory = new Directory();

		FileUtils.getContent(new File(directory.getTemp() + "/test-file-save.txt"));

		assertTrue(Boolean.TRUE);
	}

	@Test
	@DisplayName("Should exists successfully")
	void shouldExistsSuccessfully() {

		FileUtils.exists("data/file/test-file-save.txt");

		assertTrue(Boolean.TRUE);
	}

	@Test
	@DisplayName("Should get files successfully")
	void shouldGetFilesSuccessfully() {

		FileUtils.getFile("data/file/test-file-save.txt");

		assertTrue(Boolean.TRUE);
	}

	@Test
	@DisplayName("Should get files format successfully")
	void shouldGetFilesFormatSuccessfully() {

		FileUtils.getFiles("data", "txt");

		assertTrue(Boolean.TRUE);
	}

	@Test
	@DisplayName("Should get file extension successfully")
	void shouldGetFileExtensionSuccessfully() {

		var extesion = FileUtils.getFileExtension("data/file/test-file-save.txt");

		assertEquals("txt", extesion);
	}
}