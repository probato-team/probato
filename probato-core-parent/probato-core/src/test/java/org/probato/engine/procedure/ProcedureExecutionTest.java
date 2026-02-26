package org.probato.engine.procedure;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.probato.engine.ExecutionContext;
import org.probato.model.Browser;
import org.probato.model.Dimension;
import org.probato.test.script.UC01TC01_Script;
import org.probato.test.suite.UC01_Suite;
import org.probato.type.BrowserType;
import org.probato.type.DimensionMode;

@DisplayName("UT -> ProcedureExecution")
class ProcedureExecutionTest {

	@Test
	@DisplayName("Should execute procedures unit by script class successfuly")
	void shouldLoadProceduresSuccessfully() {

		var browser = Browser.builder()
				.type(BrowserType.CHROME)
				.headless(Boolean.TRUE)
				.dimension(Dimension.builder()
						.width(1200)
						.height(800)
						.mode(DimensionMode.CUSTOM)
						.build())
				.build();
		var suite = UC01_Suite.class;
		var script = UC01TC01_Script.class;
		var data = new ExecutionContext(browser, suite, script, 0);

		var discovery = ProcedureDiscovery.get();
		var builder = ProcedureBuilder.get();
		var execution = ProcedureExecution.get();

		var procedures = discovery.discover(script);
		var units = builder.build(procedures, script);
		var result = execution.collectData(data, units);
		execution.execute(data, procedures, units, result);

		assertAll("Validate data",
				() -> assertEquals(6, units.size()));

		result.getCollecedSteps().forEach(step -> {
			assertAll("Validate data",
					() -> assertNotNull(step.hasSuccess()),
					() -> assertNotNull(step.getActionValue()),
					() -> assertNotNull(step.getStepValue()),
					() -> assertNotNull(step.getStart()),
					() -> assertNotNull(step.getEnd()),
					() -> assertNotNull(step.getSequence()),
					() -> assertNotNull(step.getMethod()),
					() -> assertNotNull(step.getPhase()),
					() -> assertNull(step.getError()));
		});

		result.getExecutedSteps().forEach(step -> {
			assertAll("Validate data",
					() -> assertNotNull(step.getActionValue()),
					() -> assertNotNull(step.getStepValue()),
					() -> assertNotNull(step.getStart()),
					() -> assertNotNull(step.getEnd()),
					() -> assertNotNull(step.getSequence()),
					() -> assertNotNull(step.getMethod()),
					() -> assertNotNull(step.getPhase()),
					() -> assertNull(step.getError()));
		});
	}

	@Test
	@DisplayName("Should valdiate when no script class")
	void shouldValidateNoClassScript() {

		var script = UC01TC01_Script.class;

		var discovery = ProcedureDiscovery.get();
		var builder = ProcedureBuilder.get();
		var execution = ProcedureExecution.get();

		var procedures = discovery.discover(script);
		var units = builder.build(procedures, script);
		execution.collectData(null, units);

		assertEquals(6, units.size());
	}

}