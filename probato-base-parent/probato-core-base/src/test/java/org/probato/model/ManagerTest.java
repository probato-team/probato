package org.probato.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("UT - Manager")
class ManagerTest {

	@Test
	@DisplayName("Should create constructor object successfully")
	void shouldCreateConstructorObjectSuccessfully() {

		var url = "url";
		var token = "token";
		var submit = Boolean.TRUE;

		var model = new Manager(url, token, submit);

		assertAll("Validate value",
				() -> assertEquals(url, model.getUrl()),
				() -> assertEquals(token, model.getToken()),
				() -> assertEquals(submit, model.isSubmit()));
	}

	@Test
	@DisplayName("Should create default constructor object successfully")
	void shouldCreateDefauldConstructorObjectSuccessfully() {

		var url = "url";
		var token = "token";
		var submit = Boolean.TRUE;

		var model = new Manager();
		model.setUrl(url);
		model.setToken(token);
		model.setSubmit(submit);

		assertAll("Validate value",
				() -> assertEquals(url, model.getUrl()),
				() -> assertEquals(token, model.getToken()),
				() -> assertEquals(submit, model.isSubmit()));
	}

	@Test
	@DisplayName("Should create build object successfully")
	void shouldCreateBuildObjectSuccessfully() {

		var url = "url";
		var token = "token";
		var submit = Boolean.TRUE;

		var model = Manager.builder()
				.url(url)
				.token(token)
				.submit(submit)
				.build();

		assertAll("Validate value",
				() -> assertEquals(url, model.getUrl()),
				() -> assertEquals(token, model.getToken()),
				() -> assertEquals(submit, model.isSubmit()));
	}

}