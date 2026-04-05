package org.probato.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("UT - Directory")
class DirectoryTest {

	@Test
	@DisplayName("Should create constructor object successfully")
	void shouldCreateConstructorObjectSuccessfully() {

		var keepClean = Boolean.TRUE;

		var model = new Directory(keepClean);

		assertAll("Validate value",
				() -> assertTrue(model.getTemp().contains(".probato")),
				() -> assertEquals(keepClean, model.isKeepClean()));
	}

	@Test
	@DisplayName("Should create default constructor object successfully")
	void shouldCreateDefauldConstructorObjectSuccessfully() {

		var keepClean = Boolean.TRUE;

		var model = new Directory();
		model.setKeepClean(keepClean);

		assertAll("Validate value",
				() -> assertTrue(model.getTemp().contains(".probato")),
				() -> assertTrue(model.isKeepClean()));
	}

	@Test
	@DisplayName("Should create build object successfully")
	void shouldCreateBuildObjectSuccessfully() {

		var keepClean = Boolean.TRUE;

		var model = Directory.builder()
				.keepClean(keepClean)
				.build();

		assertAll("Validate value",
				() -> assertTrue(model.getTemp().contains(".probato")),
				() -> assertEquals(keepClean, model.isKeepClean()));
	}

}