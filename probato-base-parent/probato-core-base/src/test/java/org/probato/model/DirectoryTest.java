package org.probato.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("UT - Directory")
class DirectoryTest {

	@Test
	@DisplayName("Should create constructor object successfully")
	void shouldCreateConstructorObjectSuccessfully() {

		var temp = "temp";

		var model = new Directory(temp);

		assertAll("Validate value",
				() -> assertEquals(temp, model.getTemp()));
	}

	@Test
	@DisplayName("Should create default constructor object successfully")
	void shouldCreateDefauldConstructorObjectSuccessfully() {

		var temp = "temp";

		var model = new Directory();
		model.setTemp(temp);

		assertAll("Validate value",
				() -> assertEquals(temp, model.getTemp()));
	}

	@Test
	@DisplayName("Should create build object successfully")
	void shouldCreateBuildObjectSuccessfully() {

		var temp = "temp";

		var model = Directory.builder()
				.temp(temp)
				.build();

		assertAll("Validate value",
				() -> assertEquals(temp, model.getTemp()));
	}

}