package org.probato.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("UT - Configuration")
class ConfigurationTest {

	@Test
	@DisplayName("Should create constructor object successfully")
	void shouldCreateConstructorObjectSuccessfully() {

		var execution = new Execution();
		var browsers = new Browser[] { new Browser() };
		var datasources = new HashMap<String, Datasource>();

		var model = new Configuration(execution, browsers, datasources);

		assertAll("Validate value",
				() -> assertEquals(execution, model.getExecution()),
				() -> assertEquals(browsers, model.getBrowsers()),
				() -> assertEquals(datasources, model.getDatasources()));
	}

	@Test
	@DisplayName("Should create default constructor object successfully")
	void shouldCreateDefauldConstructorObjectSuccessfully() {

		var execution = new Execution();
		var browsers = new Browser[] { new Browser() };
		var datasources = new HashMap<String, Datasource>();

		var model = new Configuration();
		model.setExecution(execution);
		model.setBrowsers(browsers);
		model.setDatasources(datasources);

		assertAll("Validate value",
				() -> assertEquals(execution, model.getExecution()),
				() -> assertEquals(browsers, model.getBrowsers()),
				() -> assertEquals(datasources, model.getDatasources()));
	}

	@Test
	@DisplayName("Should create build object successfully")
	void shouldCreateBuildObjectSuccessfully() {

		var execution = new Execution();
		var browsers = new Browser[] { new Browser() };
		var datasources = new HashMap<String, Datasource>();

		var model = Configuration.builder()
				.execution(execution)
				.browsers(browsers)
				.datasources(datasources)
				.build();

		assertAll("Validate value",
				() -> assertEquals(execution, model.getExecution()),
				() -> assertEquals(browsers, model.getBrowsers()),
				() -> assertEquals(datasources, model.getDatasources()));
	}

}