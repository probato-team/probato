package org.probato.engine.builder;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.probato.model.Browser;
import org.probato.model.Dimension;
import org.probato.test.script.UC01TC01_Script;
import org.probato.test.suite.UC01_Suite;
import org.probato.type.BrowserType;
import org.probato.type.Complexity;
import org.probato.type.DimensionMode;
import org.probato.type.Evaluation;
import org.probato.type.Flow;
import org.probato.type.Relevance;

@DisplayName("UT - Execution")
class ExecutionTest {

	@Test
	@DisplayName("Should create object successfully")
	void shouldCreateObjectSuccessfully() {

		var id = UUID.randomUUID();
		var scriptCode = "UC01TC01";
		var suiteCode = "UC01";
		var projectId = UUID.fromString("d6d4886b-1f68-44fc-9188-7323cc79a8ed");
		var projectVersion = "1.0.0";
		var increment = 1;
		var evaluation = Evaluation.SUCCESS;
		var relevance = Relevance.MEDIUM;
		var complexity = Complexity.MEDIUM;
		var flow = Flow.MAIN;
		var inclusion = "AUTOMATIC";
		var so = "Windows 10 (amd64)";
		var browserName = "browserName";
		var browserVersion = "browserVersion";
		var browserMode = DimensionMode.CUSTOM;
		var browserHeadless = Boolean.FALSE;
		var start = ZonedDateTime.now();
		var end = ZonedDateTime.now();
		var runtime = 0L;
		var motive = "motive";
		var image = UUID.randomUUID();
		var video = UUID.randomUUID();
		var datasetFilePath = List.of("data/csv/UC01TC01.csv");
		var datasetHeaders = List.of();
		var datasetContent = List.of();
		var sqlFilesPath = new HashMap<String, List<String>>();
		var preconditions = List.<Step>of();
		var procedures = List.<Step>of();
		var postconditions = List.<Step>of();
		var stepsPreconditions = List.<Step>of();
		var stepsProcedures = List.<Step>of();
		var stepsPostconditions = List.<Step>of();
		var browser = Browser.builder()
				.type(BrowserType.CHROME)
				.dimension(Dimension.builder()
						.mode(DimensionMode.CUSTOM)
						.height(1000)
						.width(800)
						.build())
				.build();

		var model = Execution.builder()
				.id(id)
				.env()
				.project()
				.suite(UC01_Suite.class)
				.script(UC01TC01_Script.class)
				.evaluation(evaluation)
				.dataset(UC01TC01_Script.class, 1)
				.browser(browser, browserName, browserVersion)
				.sql(UC01TC01_Script.class)
				.start(start)
				.end(end)
				.motive(motive)
				.image(image)
				.video(video)
				.preconditions(preconditions)
				.procedures(procedures)
				.postconditions(postconditions)
				.stepsPreconditions(stepsPreconditions)
				.stepsProcedures(stepsProcedures)
				.stepsPostconditions(stepsPostconditions)
				.build();

		assertAll("Validate value",
				() -> assertEquals(id, model.getId()),
				() -> assertEquals(scriptCode, model.getScriptCode()),
				() -> assertEquals(suiteCode, model.getSuiteCode()),
				() -> assertEquals(projectId, model.getProjectId()),
				() -> assertEquals(projectVersion, model.getProjectVersion()),
				() -> assertEquals(increment, model.getIncrement()),
				() -> assertEquals(evaluation, model.getEvaluation()),
				() -> assertEquals(relevance, model.getRelevance()),
				() -> assertEquals(complexity, model.getComplexity()),
				() -> assertEquals(flow, model.getFlow()),
				() -> assertEquals(inclusion, model.getInclusion()),
				() -> assertEquals(so, model.getSo()),
				() -> assertEquals(browserName, model.getBrowserName()),
				() -> assertEquals(browserVersion, model.getBrowserVersion()),
				() -> assertEquals(browserMode, model.getBrowserMode()),
				() -> assertEquals(browserHeadless, model.getBrowserHeadless()),
				() -> assertEquals(start, model.getStart()),
				() -> assertEquals(end, model.getEnd()),
				() -> assertEquals(runtime, model.getRuntime()),
				() -> assertEquals(motive, model.getMotive()),
				() -> assertEquals(image, model.getImage()),
				() -> assertEquals(runtime, model.getRuntime()),
				() -> assertEquals(video, model.getVideo()),
				() -> assertEquals(datasetFilePath, model.getDatasetFilePath()),
				() -> assertEquals(datasetHeaders, model.getDatasetHeaders()),
				() -> assertEquals(datasetContent, model.getDatasetContent()),
				() -> assertEquals(sqlFilesPath, model.getSqlFilesPath()),
				() -> assertEquals(preconditions, model.getPreconditions()),
				() -> assertEquals(procedures, model.getProcedures()),
				() -> assertEquals(postconditions, model.getPostconditions()),
				() -> assertEquals(stepsPreconditions, model.getStepsPreconditions()),
				() -> assertEquals(stepsProcedures, model.getStepsProcedures()),
				() -> assertEquals(stepsPostconditions, model.getStepsPostconditions()));
	}

}