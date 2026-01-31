package org.probato.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("UT - Datamodel")
class DatamodelTest {

	@Test
	@DisplayName("Should create default constructor object successfully")
	void shouldCreateDefauldConstructorObjectSuccessfully() {

		var model = new Datamodel();

		assertAll("Validate value",
				() -> assertNotNull(model));
	}

}