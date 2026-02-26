package org.probato.dataset;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.annotation.Annotation;
import java.util.ServiceLoader;
import java.util.ServiceLoader.Provider;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.probato.api.Dataset;
import org.probato.exception.IntegrityException;
import org.probato.loader.DatasetLoader;
import org.probato.test.datamodel.DataInvalid;
import org.probato.test.datamodel.LoginModel;
import org.probato.test.script.UC01TC01_ScriptDataset;
import org.probato.test.script.UC02TC01_ScriptDatasetNotFound;

@DisplayName("UT - JsonDatasetProvider")
class JsonDatasetProviderTest {

	private DatasetProvider service;

	@BeforeEach
	void init() {
		service = ServiceLoader.load(DatasetProvider.class)
				.stream()
				.map(Provider::get)
				.collect(Collectors.toList())
				.get(0);
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

		var dataset = new Dataset() {

			@Override
			public Class<? extends Annotation> annotationType() {
				return null;
			}

			@Override
			public String value() {
				return path;
			}
		};

		var result = service.accepted(dataset);

		assertEquals(expected, result);
	}

	@Test
	@DisplayName("Should count lines file JSON successfully")
	void shouldCountSuccessfully() throws Exception {

		var dataset = DatasetLoader.getDataset(UC01TC01_ScriptDataset.class)
				.orElseThrow(() -> new Exception("Not found"));

		int lines = service.countEntries(dataset);

		assertEquals(2, lines);
	}

	@Test
	@DisplayName("Should validate file count not found")
	void shouldCountFileNotFound() throws Exception {

		var dataset = DatasetLoader.getDataset(UC02TC01_ScriptDatasetNotFound.class)
				.orElseThrow(() -> new Exception("Not found"));

		var exception = assertThrows(IntegrityException.class, () -> service.countEntries(dataset));
		assertEquals("Load file 'path/to/file-not-found.json' error: File not found", exception.getMessage());
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
	@DisplayName("Should dataline file JSON successfully")
	void shouldDatalineEmptyObjectSuccessfully() throws Exception {

		var dataset = DatasetLoader.getDataset(UC01TC01_ScriptDataset.class)
				.orElseThrow(() -> new Exception("Not found"));

		var datamodel = service.getDatamodel(dataset, LoginModel.class, -1);

		assertNotNull(datamodel);
	}

	@Test
	@DisplayName("Should validate invalid file path")
	void shouldValidateFilePath() {

		var dataset = new Dataset() {

			@Override
			public Class<? extends Annotation> annotationType() {
				return null;
			}

			@Override
			public String value() {
				return "";
			}
		};

		var exception = assertThrows(IntegrityException.class, () -> service.countEntries(dataset));
		assertEquals("Dataset path not defined", exception.getMessage());
	}

	@Test
	@DisplayName("Should validate class with invalid constructor")
	void shouldValidateClassInvalidConstructor() throws Exception {

		var dataset = DatasetLoader.getDataset(UC01TC01_ScriptDataset.class)
				.orElseThrow(() -> new Exception("Not found"));

		var exception = assertThrows(IntegrityException.class, () -> service.getDatamodel(dataset, DataInvalid.class, -1));
		assertEquals("Class must have default constructor: 'org.probato.test.datamodel.DataInvalid'", exception.getMessage());
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