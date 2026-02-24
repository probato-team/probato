package org.probato.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("UT - Content")
class ContentTest {

	@Test
	@DisplayName("Should create constructor object successfully")
	void shouldCreateConstructorObjectSuccessfully() {

		var headers = new String[] {"header"};
		var data = new String[] {"data"};

		var model = new Content(headers, data);

		assertAll("Validate value",
				() -> assertEquals(headers, model.getHeaders()),
				() -> assertEquals(data, model.getData()));
	}

	@Test
	@DisplayName("Should create default constructor object successfully")
	void shouldCreateDefauldConstructorObjectSuccessfully() {

		var model = new Content();

		assertAll("Validate value",
				() -> assertNotNull(model.getHeaders()),
				() -> assertNotNull(model.getData()));
	}

	@Test
	@DisplayName("Should create build object successfully")
	void shouldCreateBuildObjectSuccessfully() {

		var headers = new String[] {"header"};
		var data = new String[] {"data"};

		var model = Content.builder()
				.headers(headers)
				.data(data)
				.build();

		assertAll("Validate value",
				() -> assertEquals(headers, model.getHeaders()),
				() -> assertEquals(data, model.getData()));
	}

}