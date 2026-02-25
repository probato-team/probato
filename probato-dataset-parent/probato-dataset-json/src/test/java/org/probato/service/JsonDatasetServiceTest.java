package org.probato.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.probato.exception.IntegrityException;
import org.probato.loader.DatasetLoader;
import org.probato.test.datamodel.LoginModel;
import org.probato.test.script.UC01TC01_ScriptDataset;
import org.probato.test.script.UC02TC01_ScriptDatasetNotFound;

@DisplayName("UT - JsonDatasetService")
class JsonDatasetServiceTest {

	private DatasetService service;

	@BeforeEach
	void init() {
		service = DatasetService.get();
	}

	@ParameterizedTest
	@CsvSource({
		"file.json,true",
		"FILE.JSON,true",
		"file.csv,false",
		"FILE.CSV,false",
	})
	@DisplayName("Should accepted file JSON successfully")
	void shouldAcceptedSuccessfully(String path, boolean expected) {

		var result = service.accepted(path);

		assertEquals(expected, result);
	}

	@Test
	@DisplayName("Should count lines file JSON successfully")
	void shouldCountSuccessfully() throws Exception {

		var dataset = DatasetLoader.getDataset(UC01TC01_ScriptDataset.class)
				.orElseThrow(() -> new Exception("Not found"));

		int lines = service.counterLines(dataset);

		assertEquals(2, lines);
	}

	@Test
	@DisplayName("Should dataline file JSON successfully")
	void shouldDatalineSuccessfully() throws Exception {

		var dataset = DatasetLoader.getDataset(UC01TC01_ScriptDataset.class)
				.orElseThrow(() -> new Exception("Not found"));

		var datamodel = service.getDatamodel(dataset, LoginModel.class, 0);

		assertNotNull(datamodel);
	}

	@Test
	@DisplayName("Should throw exception for invalid index")
	void shouldThrowExceptionForInvalidIndex() throws Exception {

		var dataset = DatasetLoader.getDataset(UC01TC01_ScriptDataset.class)
				.orElseThrow(() -> new Exception("Not found"));

		assertThrows(IndexOutOfBoundsException.class, () -> service.getDatamodel(dataset, LoginModel.class, 99));
	}

	@Test
	@DisplayName("Should datalines file JSON successfully")
	void shouldDatalinesSuccessfully() throws Exception {

		var dataset = DatasetLoader.getDataset(UC01TC01_ScriptDataset.class)
				.orElseThrow(() -> new Exception("Not found"));

		var datamodels = service.getDatamodels(dataset);

		assertEquals(2, datamodels.size());
	}

	@Test
	@DisplayName("Should validate file count not found")
	void shouldCountFileNotFound() throws Exception {

		var dataset = DatasetLoader.getDataset(UC02TC01_ScriptDatasetNotFound.class)
				.orElseThrow(() -> new Exception("Not found"));

		var exception = assertThrows(IntegrityException.class, () -> service.counterLines(dataset));
		assertEquals("Load file 'path/to/file-not-found.json' error: File not found", exception.getMessage());
	}

	@Test
	@DisplayName("Should validate dataline file not found")
	void shouldDatalineFileNotFound() throws Exception {

		var dataset = DatasetLoader.getDataset(UC02TC01_ScriptDatasetNotFound.class)
				.orElseThrow(() -> new Exception("Not found"));

		var exception = assertThrows(IntegrityException.class, () -> service.getDatamodels(dataset));
		assertEquals("Load file 'path/to/file-not-found.json' error: File not found", exception.getMessage());
	}

	@Test
	@DisplayName("Should content file JSON successfully")
	void shouldContentSuccessfully() throws Exception {

		var dataset = DatasetLoader.getDataset(UC01TC01_ScriptDataset.class)
				.orElseThrow(() -> new Exception("Not found"));

		var content = service.getContent(dataset, -1);

		assertNotNull(content);
	}

	@Test
	@DisplayName("Should content line file JSON successfully")
	void shouldContentLineSuccessfully() throws Exception {

		var dataset = DatasetLoader.getDataset(UC01TC01_ScriptDataset.class)
				.orElseThrow(() -> new Exception("Not found"));

		var content = service.getContent(dataset, 1);

		assertNotNull(content);
	}


	@Test
	@DisplayName("Should validate content file not found")
	void shouldContentFileNotFound() throws Exception {

		var dataset = DatasetLoader.getDataset(UC02TC01_ScriptDatasetNotFound.class)
				.orElseThrow(() -> new Exception("Not found"));

		var exception = assertThrows(IntegrityException.class, () -> service.getContent(dataset, 1));
		assertEquals("Load file 'path/to/file-not-found.json' error: File not found", exception.getMessage());
	}

}