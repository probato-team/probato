package org.probato.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.probato.core.loader.AnnotationLoader;
import org.probato.exception.IntegrityException;
import org.probato.test.datamodel.Data;
import org.probato.test.datamodel.DataInvalidConstructor;
import org.probato.test.script.UC07TC01_ScriptWithInvalidDatamodel;
import org.probato.test.script.UC10TC01_ScriptDataset;
import org.probato.test.script.UC10TC01_ScriptDatasetNotFound;

@DisplayName("Test -> CsvDatasetService")
class CsvDatasetServiceTest {

	private DatasetService service;

	@BeforeEach
	void init() {
		service = DatasetService.getInstance();
	}

	@Test
	@DisplayName("Should count lines file CSV successfully")
	void shouldCountSuccessfully() throws Exception {

		var dataset = AnnotationLoader.getDataset(UC10TC01_ScriptDataset.class)
				.orElseThrow(() -> new Exception("Not found"));

		int lines = service.counterLines(dataset);

		assertEquals(5, lines);
	}

	@ParameterizedTest
	@ValueSource(ints = {-1, 0})
	@DisplayName("Should dataline file CSV successfully")
	void shouldDatalineSuccessfully(Integer index) throws Exception {

		var dataset = AnnotationLoader.getDataset(UC10TC01_ScriptDataset.class)
				.orElseThrow(() -> new Exception("Not found"));

		var datamodel = service.getDatamodel(dataset, Data.class, index);

		assertNotNull(datamodel);
	}

	@Test
	@DisplayName("Should validate when invalid datamodel constructor")
	void shouldInvalidaDatamodelConstructor() throws Exception {

		var dataset = AnnotationLoader.getDataset(UC07TC01_ScriptWithInvalidDatamodel.class)
				.orElseThrow(() -> new Exception("Not found"));

		var exception = assertThrows(IntegrityException.class,
				() -> service.getDatamodel(dataset, DataInvalidConstructor.class, -1));

		assertEquals("Class must have default constructor: 'org.probato.test.datamodel.DataInvalidConstructor'", exception.getMessage());
	}

	@Test
	@DisplayName("Should datalines file CSV successfully")
	void shouldDatalinesSuccessfully() throws Exception {

		var dataset = AnnotationLoader.getDataset(UC10TC01_ScriptDataset.class)
				.orElseThrow(() -> new Exception("Not found"));

		var datamodels = service.getDatamodels(dataset);

		assertEquals(5, datamodels.size());
	}

	@Test
	@DisplayName("Should validate file count not found")
	void shouldCountFileNotFound() throws Exception {

		var dataset = AnnotationLoader.getDataset(UC10TC01_ScriptDatasetNotFound.class)
				.orElseThrow(() -> new Exception("Not found"));

		var exception = assertThrows(IntegrityException.class, () -> service.counterLines(dataset));
		assertEquals("Load file 'path/to/file-not-found.csv' error: File not found", exception.getMessage());
	}

	@Test
	@DisplayName("Should validate dataline file not found")
	void shouldDatalineFileNotFound() throws Exception {

		var dataset = AnnotationLoader.getDataset(UC10TC01_ScriptDatasetNotFound.class)
				.orElseThrow(() -> new Exception("Not found"));

		var exception = assertThrows(IntegrityException.class, () -> service.getDatamodels(dataset));
		assertEquals("Load file 'path/to/file-not-found.csv' error: File not found", exception.getMessage());
	}

	@Test
	@DisplayName("Should content file CSV successfully")
	void shouldContentSuccessfully() throws Exception {

		var dataset = AnnotationLoader.getDataset(UC10TC01_ScriptDataset.class)
				.orElseThrow(() -> new Exception("Not found"));

		var content = service.getContent(dataset, -1);

		assertNotNull(content);
	}

	@Test
	@DisplayName("Should content line file CSV successfully")
	void shouldContentLineSuccessfully() throws Exception {

		var dataset = AnnotationLoader.getDataset(UC10TC01_ScriptDataset.class)
				.orElseThrow(() -> new Exception("Not found"));

		var content = service.getContent(dataset, 2);

		assertNotNull(content);
	}

	@Test
	@DisplayName("Should content line file CSV successfully")
	void shouldContentLineNotFound() throws Exception {

		var dataset = AnnotationLoader.getDataset(UC10TC01_ScriptDataset.class)
				.orElseThrow(() -> new Exception("Not found"));

		var content = service.getContent(dataset, -1);

		assertNotNull(content);
	}

}