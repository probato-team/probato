package org.probato.node;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicNode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.probato.api.Script;
import org.probato.model.Browser;
import org.probato.model.Browser.BrowserBuilder;
import org.probato.model.Dimension;
import org.probato.model.type.BrowserType;
import org.probato.model.type.DimensionMode;
import org.probato.test.script.UC01TC01_Script;
import org.probato.test.suite.UC01_Suite;

@DisplayName("Test - TestNodeService")
class TestNodeServiceTest {

	@Test
	@DisplayName("Should create a script test node successfully")
	void shouldCreateScriptTestNodeSuccessfully() {

		var script = UC01TC01_Script.class.getAnnotation(Script.class);
		var subList = new ArrayList<DynamicNode>();

		var dynamicNode = TestNodeService.createScriptTestNode(script, subList);

		assertEquals("UC01TC01 - Test case 01", dynamicNode.getDisplayName());
	}

	@Test
	@DisplayName("Should create a script group test node successfully")
	void shouldCreateScriptGroupTestNodeSuccessfully() {

		var subList = new ArrayList<DynamicNode>();

		var dynamicNode = TestNodeService.createScriptTestNode(UC01TC01_Script.class, subList);

		assertEquals("class org.probato.test.script.UC01TC01_Script", dynamicNode.getDisplayName());
	}

	@Test
	@DisplayName("Should create a dataset test node successfully")
	void shouldCreateDatasetTestNodeSuccessfully() {

		int number = 1;
		var subList = new ArrayList<DynamicNode>();

		var dynamicNode = TestNodeService.createDatasetTestNode(number, subList);

		assertEquals("Dataset 1", dynamicNode.getDisplayName());
	}

	@ParameterizedTest
	@MethodSource("getBrowsers")
	@DisplayName("Should create a browser test node successfully")
	void shouldCreateBrowserTestNodeSuccessfully(Browser browser, String description) {

		var dynamicNode = TestNodeService
				.createBrowserTestNode(UC01_Suite.class, UC01TC01_Script.class, browser, 0);

		assertEquals(description, dynamicNode.getDisplayName());
	}

	private static Stream<Arguments> getBrowsers() {
		return Stream.of(
				Arguments.of(getBrowser().build(), "Google Chrome - Maximized"),
				Arguments.of(getBrowser()
						.type(BrowserType.FIREFOX)
						.dimension(Dimension.builder()
								.mode(DimensionMode.CUSTOM)
								.height(1)
								.width(1)
								.build())
						.build(), "Mozilla Firefox - Custom (1x1)"),
				Arguments.of(getBrowser()
						.type(BrowserType.EDGE)
						.build(), "Microsoft Edge - Maximized"));
	}

	public static BrowserBuilder getBrowser() {
		return Browser.builder()
				.dimension(Dimension.builder()
						.mode(DimensionMode.MAXIMIZED)
						.build())
				.headless(Boolean.TRUE)
				.type(BrowserType.CHROME);
	}

}