package org.probato.engine.builder;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.probato.test.suite.UC01_Suite;
import org.probato.type.Inclusion;

@DisplayName("UT - Suite")
class SuiteTest {

	@Test
	@DisplayName("Should create object successfully")
	void shouldCreateObjectSuccessfully() {

		var code = "UC01";
		var name = "Suite 01";
		var description = "This a simple test";
		var clazz = UC01_Suite.class.getName();
		var projectId = UUID.fromString("d6d4886b-1f68-44fc-9188-7323cc79a8ed");
		var projectVersion = "1.0.0";
		var deprecated = Boolean.FALSE;
		var inclusion = Inclusion.AUTOMATIC;

		var model = Suite.builder()
				.code(code)
				.clazz(clazz)
				.description(description)
				.projectId(projectId)
				.projectVersion(projectVersion)
				.deprecated(deprecated)
				.inclusion(inclusion)
				.clazz(UC01_Suite.class)
				.build();

		assertAll("Validate value",
				() -> assertEquals(code, model.getCode()),
				() -> assertEquals(name, model.getName()),
				() -> assertNotNull(description, model.getDescription()),
				() -> assertEquals(clazz, model.getClazz()),
				() -> assertEquals(projectId, model.getProjectId()),
				() -> assertEquals(projectVersion, model.getProjectVersion()),
				() -> assertEquals(deprecated, model.isDeprecated()),
				() -> assertEquals(inclusion, model.getInclusion()));
	}

}