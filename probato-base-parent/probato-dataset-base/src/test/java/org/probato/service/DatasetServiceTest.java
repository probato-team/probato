package org.probato.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.annotation.Annotation;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.probato.api.Dataset;
import org.probato.datamodel.Data;
import org.probato.exception.IntegrityException;

@Disabled
@DisplayName("UT - DatasetService")
class DatasetServiceTest {

	@Test
	@DisplayName("Should execute dataset service successfully")
	void shouldExecuteValidatorSuccessfully() {

		var dataset = new Dataset() {

			@Override
			public Class<? extends Annotation> annotationType() {
				return null;
			}

			@Override
			public String value() {
				return "path/to/file.csv";
			}
		};

		var service = DatasetService.get();

		service.getDatamodel(dataset, Data.class, 0);
		service.getDatamodel(dataset, Data.class, 0);
		service.getDatamodels(dataset);
		var datamodels = service.getDatamodels(dataset, Data.class);
		var content = service.getContent(dataset, 0);
		var counterLines = service.countEntries(dataset);

		assertAll("Validate data",
				() -> assertNotNull(datamodels),
				() -> assertNotNull(content),
				() -> assertEquals(0, counterLines));
	}

	@Test
	@DisplayName("Should validate when instance no datamodel object")
	void shouldValidateInstanceDatamodel() {

		var service = DatasetService.get();

		var exception = assertThrows(IntegrityException.class,
				() -> service.getDatamodel(null, DatasetServiceTest.class, 0));

		assertEquals("Class must have default constructor: 'org.probato.service.DatasetServiceTest'", exception.getMessage());
	}

}
