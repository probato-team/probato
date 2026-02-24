package org.probato.junit;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.probato.model.Browser;
import org.probato.model.Browser.BrowserBuilder;
import org.probato.model.Dimension;
import org.probato.model.type.BrowserType;
import org.probato.model.type.DimensionMode;
import org.probato.node.TestNodeExecutable;
import org.probato.test.script.UC01TC01_Script;
import org.probato.test.suite.UC01_Suite;

@DisplayName("Test - TestNodeExecutable")
class TestNodeExecutableTest {

	@ParameterizedTest
	@MethodSource("getBrowsers")
	@DisplayName("Should execute test successfully")
	void shouldExecuteBrowserSuccessfully(Browser browser) throws Throwable {

		new TestNodeExecutable(UC01_Suite.class, UC01TC01_Script.class, browser, 0).execute();

		assertTrue(Boolean.TRUE);
	}

	@Test
	@DisplayName("Should execute test successfully")
	void shouldExecuteDesktopSuccessfully() throws Throwable {
		
		new TestNodeExecutable(UC01_Suite.class, UC01TC01_Script.class, 0).execute();
		
		assertTrue(Boolean.TRUE);
	}

	private static Stream<Arguments> getBrowsers() {
		return Stream.of(
				Arguments.of(getBrowser().build()),
				Arguments.of(getBrowser().type(BrowserType.FIREFOX).build()),
				Arguments.of(getBrowser().type(BrowserType.EDGE).build()));
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