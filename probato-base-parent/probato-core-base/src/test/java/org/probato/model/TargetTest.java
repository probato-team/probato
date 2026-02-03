package org.probato.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("UT - Target")
class TargetTest {

	@Test
	@DisplayName("Should create constructor object successfully")
	void shouldCreateConstructorObjectSuccessfully() {

		var projectId = UUID.randomUUID();
		var url = "url";
		var version = "version";

		var model = new Target(projectId, url, version);

		assertAll("Validate value",
				() -> assertEquals(projectId, model.getProjectId()),
				() -> assertEquals(url, model.getUrl()),
				() -> assertEquals(version, model.getVersion()));
	}

	@Test
	@DisplayName("Should create default constructor object successfully")
	void shouldCreateDefauldConstructorObjectSuccessfully() {

		var projectId = UUID.randomUUID();
		var url = "url";
		var version = "version";

		var model = new Target();
		model.setProjectId(projectId);
		model.setUrl(url);
		model.setVersion(version);

		assertAll("Validate value",
				() -> assertEquals(projectId, model.getProjectId()),
				() -> assertEquals(url, model.getUrl()),
				() -> assertEquals(version, model.getVersion()));
	}

	@Test
	@DisplayName("Should create build object successfully")
	void shouldCreateBuildObjectSuccessfully() {

		var projectId = UUID.randomUUID();
		var url = "url";
		var version = "version";

		var model = Target.builder()
				.projectId(projectId)
				.url(url)
				.version(version)
				.build();

		assertAll("Validate value",
				() -> assertEquals(projectId, model.getProjectId()),
				() -> assertEquals(url, model.getUrl()),
				() -> assertEquals(version, model.getVersion()));
	}

}