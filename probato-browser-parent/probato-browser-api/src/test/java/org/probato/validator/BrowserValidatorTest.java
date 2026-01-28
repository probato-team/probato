package org.probato.validator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.probato.exception.IntegrityException;
import org.probato.model.Browser;
import org.probato.model.Dimension;
import org.probato.type.BrowserType;
import org.probato.type.DimensionMode;

@DisplayName("UT - Chrome validator")
class BrowserValidatorTest {

	@ParameterizedTest
	@MethodSource("getParams")
	@DisplayName("Should run validate browser properties successfully")
	void shouldRunValidateBrowserPropertiesSuccessfully(Browser browser) {

		var validator = new BrowserValidator();

		validator.execute(new Browser[] {browser});

		assertTrue(Boolean.TRUE);
	}

	@ParameterizedTest
	@MethodSource("getInvalidParams")
	@DisplayName("Should validate invalid properties")
	void shouldValidateInvalidProperties(Browser [] browsers, String expected) {

		var validator = new BrowserValidator();

		var exception = assertThrows(IntegrityException.class, () -> validator.execute(browsers));

		assertEquals(expected, exception.getMessage());
	}

	private static Stream<Arguments> getParams() {
		return Stream.of(
				Arguments.of(
					new Browser(BrowserType.CHROME, Boolean.TRUE, new Dimension(null, null, DimensionMode.FULLSCREEN))
				),
				Arguments.of(
					new Browser(BrowserType.CHROME, Boolean.FALSE, new Dimension(null, null, DimensionMode.MAXIMIZED))
				),
				Arguments.of(
					new Browser(BrowserType.CHROME, Boolean.TRUE, new Dimension(1200, 850, DimensionMode.CUSTOM))
				));
	}

	private static Stream<Arguments> getInvalidParams() {
		return Stream.of(
				Arguments.of(
					null,
					"Property 'browsers' should be declared in 'configuration.yaml' file"
				),
				Arguments.of(
						new Browser[] {},
						"Property 'browsers' should be declared in 'configuration.yaml' file"
						),
				Arguments.of(
					new Browser[] {new Browser(null, Boolean.TRUE, new Dimension(null, null, DimensionMode.FULLSCREEN))},
					"Property 'browsers.[0].type' should be declared in 'configuration.yaml' file"
				),
				Arguments.of(
					new Browser[] {new Browser(BrowserType.CHROME, Boolean.FALSE, null)},
					"Property 'browsers.[0].dimension.mode' should be declared in 'configuration.yaml' file"
				),
				Arguments.of(
					new Browser[] {new Browser(BrowserType.CHROME, Boolean.FALSE, new Dimension(null, null, null))},
					"Property 'browsers.[0].dimension.mode' should be declared in 'configuration.yaml' file"
				),
				Arguments.of(
					new Browser[] {new Browser(BrowserType.CHROME, Boolean.TRUE, new Dimension(null, 850, DimensionMode.CUSTOM))},
					"Property 'browsers.[0].dimension.height' and 'browsers.[0].dimension.width' should be declared in 'configuration.yaml' file when 'browsers.[0].dimension.mode' equals CUSTOM"
				),
				Arguments.of(
					new Browser[] {new Browser(BrowserType.CHROME, Boolean.TRUE, new Dimension(1200, null, DimensionMode.CUSTOM))},
					"Property 'browsers.[0].dimension.height' and 'browsers.[0].dimension.width' should be declared in 'configuration.yaml' file when 'browsers.[0].dimension.mode' equals CUSTOM"
				));
	}

}