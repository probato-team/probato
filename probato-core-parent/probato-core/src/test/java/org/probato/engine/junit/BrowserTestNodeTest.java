package org.probato.engine.junit;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicNode;
import org.junit.jupiter.api.Test;
import org.probato.model.Browser;
import org.probato.model.Dimension;
import org.probato.test.script.UC01TC01_Script;
import org.probato.test.suite.UC01_Suite;
import org.probato.type.BrowserType;
import org.probato.type.DimensionMode;

@DisplayName("UT -> BrowserTestNode")
class BrowserTestNodeTest {

	@Test
	@DisplayName("Should create script test node successfuly")
	void shouldCreateScriptTestNodeSuccessfully() {

		var script = UC01TC01_Script.class;
		var nodes = List.<DynamicNode>of();

		var node = TestNodeService.createScriptTestNode(script, nodes);

		assertNotNull(node);
	}

	@Test
	@DisplayName("Should create dataset test node successfuly")
	void shouldCreateDatasetTestNodeSuccessfully() {

		var script = UC01TC01_Script.class;
		var numberLine = 1;
		var nodes = List.<DynamicNode>of();

		var node = TestNodeService.createDatasetTestNode(script, numberLine, nodes);

		assertNotNull(node);
	}

	@Test
	@DisplayName("Should create browser test node successfuly")
	void shouldCreateBrowserTestNodeSuccessfully() {

		var browser = Browser.builder()
				.type(BrowserType.CHROME)
				.dimension(Dimension.builder()
						.mode(DimensionMode.FULLSCREEN)
						.build())
				.build();
		var suite = UC01_Suite.class;
		var script = UC01TC01_Script.class;
		var numberLine = 1;

		var node = TestNodeService.createBrowserTestNode(browser, suite, script, numberLine);

		assertNotNull(node);
	}

	@Test
	@DisplayName("Should create browser test node custom successfuly")
	void shouldCreateBrowserTestNodeCusomSuccessfully() {

		var browser = Browser.builder()
				.type(BrowserType.CHROME)
				.dimension(Dimension.builder()
						.mode(DimensionMode.CUSTOM)
						.height(1000)
						.width(800)
						.build())
				.build();
		var suite = UC01_Suite.class;
		var script = UC01TC01_Script.class;
		var numberLine = 1;

		var node = TestNodeService.createBrowserTestNode(browser, suite, script, numberLine);

		assertNotNull(node);
	}

}