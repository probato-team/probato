package org.probato.engine.builder;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.probato.test.script.UC01TC01_Script;
import org.probato.test.suite.UC01_Suite;
import org.probato.type.Complexity;
import org.probato.type.Flow;
import org.probato.type.Inclusion;
import org.probato.type.Relevance;

@DisplayName("UT - Script")
class ScriptTest {

	@Test
	@DisplayName("Should create object successfully")
	void shouldCreateObjectSuccessfully() {

		var code = "UC01TC01";
		var name = "Test case 01";
		var description = "This a simple test";
		var clazz = UC01TC01_Script.class.getName().toString();
		var suiteCode = "UC01";
		var projectId = UUID.fromString("d6d4886b-1f68-44fc-9188-7323cc79a8ed");
		var projectVersion = "1.0.0";
		var deprecated = Boolean.FALSE;
		var relevance = Relevance.MEDIUM;
		var complexity = Complexity.MEDIUM;
		var flow = Flow.MAIN;
		var inclusion = Inclusion.AUTOMATIC;
		var preconditions = List.<Step>of();
		var procedures = List.<Step>of();
		var postconditions = List.<Step>of();

		var model = Script.builder()
				.code(code)
				.name(name)
				.description(description)
				.clazz(clazz)
				.suiteCode(suiteCode)
				.projectId(projectId)
				.projectVersion(projectVersion)
				.deprecated(deprecated)
				.relevance(relevance)
				.complexity(complexity)
				.flow(flow)
				.inclusion(inclusion)
				.preconditions(preconditions)
				.procedures(procedures)
				.postconditions(postconditions)
				.clazz(UC01_Suite.class, UC01TC01_Script.class)
				.build();

		assertAll("Validate value",
				() -> assertEquals(code, model.getCode()),
				() -> assertEquals(name, model.getName()),
				() -> assertEquals(description, model.getDescription()),
				() -> assertEquals(clazz, model.getClazz()),
				() -> assertEquals(deprecated, model.isDeprecated()),
				() -> assertEquals(suiteCode, model.getSuiteCode()),
				() -> assertEquals(projectId, model.getProjectId()),
				() -> assertEquals(projectVersion, model.getProjectVersion()),
				() -> assertEquals(relevance, model.getRelevance()),
				() -> assertEquals(complexity, model.getComplexity()),
				() -> assertEquals(flow, model.getFlow()),
				() -> assertEquals(inclusion, model.getInclusion()),
				() -> assertEquals(preconditions, model.getPreconditions()),
				() -> assertEquals(procedures, model.getProcedures()),
				() -> assertEquals(postconditions, model.getPostconditions()));
	}

}