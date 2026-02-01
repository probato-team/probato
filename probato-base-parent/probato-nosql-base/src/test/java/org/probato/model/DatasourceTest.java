package org.probato.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("UT - Datasource")
class DatasourceTest {

	@Test
	@DisplayName("Should create constructor object successfully")
	void shouldCreateConstructorObjectSuccessfully() {

		var url = "url";
		var schema = "schema";
		var driver = "driver";
		var username = "username";
		var password = "password";

		var model = new Datasource(url, schema, driver, username, password);

		assertAll("Validate value",
				() -> assertEquals(url, model.getUrl()),
				() -> assertEquals(schema, model.getSchema()),
				() -> assertEquals(driver, model.getDriver()),
				() -> assertEquals(username, model.getUsername()),
				() -> assertEquals(password, model.getPassword()));
	}

	@Test
	@DisplayName("Should create default constructor object successfully")
	void shouldCreateDefauldConstructorObjectSuccessfully() {

		var url = "url";
		var schema = "schema";
		var driver = "driver";
		var username = "username";
		var password = "password";

		var model = new Datasource();
		model.setUrl(url);
		model.setSchema(schema);
		model.setDriver(driver);
		model.setUsername(username);
		model.setPassword(password);

		assertAll("Validate value",
				() -> assertEquals(url, model.getUrl()),
				() -> assertEquals(schema, model.getSchema()),
				() -> assertEquals(driver, model.getDriver()),
				() -> assertEquals(username, model.getUsername()),
				() -> assertEquals(password, model.getPassword()));
	}

	@Test
	@DisplayName("Should create build object successfully")
	void shouldCreateBuildObjectSuccessfully() {

		var url = "url";
		var schema = "schema";
		var driver = "driver";
		var username = "username";
		var password = "password";

		var model = Datasource.builder()
				.url(url)
				.schema(schema)
				.driver(driver)
				.username(username)
				.password(password)
				.build();

		assertAll("Validate value",
				() -> assertEquals(url, model.getUrl()),
				() -> assertEquals(schema, model.getSchema()),
				() -> assertEquals(driver, model.getDriver()),
				() -> assertEquals(username, model.getUsername()),
				() -> assertEquals(password, model.getPassword()));
	}

}