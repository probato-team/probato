package org.probato.engine;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.probato.model.Browser;
import org.probato.model.Dimension;
import org.probato.test.script.UC01TC01_Script;
import org.probato.test.suite.UC01_Suite;
import org.probato.type.BrowserType;
import org.probato.type.DimensionMode;

@DisplayName("UT - ExecutionEngine")
class ExecutionEngineIT {

	@Test
	@DisplayName("Should execution engine successfully")
	void shouldRunExecutionEngineSuccessfully() {

		var browser = Browser.builder()
				.type(BrowserType.CHROME)
				.headless(Boolean.TRUE)
				.dimension(Dimension.builder()
						.width(1200)
						.height(800)
						.mode(DimensionMode.CUSTOM)
						.build())
				.build();

		var data = new ExecutionContext(browser, UC01_Suite.class, UC01TC01_Script.class, 0);

		var engine = ExecutionEngine.get();

		engine.execute(data);

		assertTrue(Boolean.TRUE);
	}

}