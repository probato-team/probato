package org.probato.type;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.probato.exception.IntegrityException;

@DisplayName("UT - Browser type")
class BrowserTypeTest {

	@ParameterizedTest
	@MethodSource("getParams")
	@DisplayName("Should conveter value successfully")
	void shouldConverterValueSuccessfully(String value, BrowserType expected) {

		var browserType = BrowserType.fromString(value);

		assertAll("Validate value",
				() -> assertEquals(expected, browserType),
				() -> assertEquals(expected.description(), browserType.description()));
	}

	@Test
	@DisplayName("Should validate when invalid value")
	void shouldValidateInvalidValue() {

		var exception = assertThrows(IntegrityException.class, () -> BrowserType.fromString("OTHER"));

		assertEquals("Invalid value for BrowserType property: OTHER", exception.getMessage());
	}

	private static Stream<Arguments> getParams() {
		return Stream.of(
			Arguments.of(
				"CHROME",
				BrowserType.CHROME
			),
			Arguments.of(
				"FIREFOX",
				BrowserType.FIREFOX
			),
			Arguments.of(
				"EDGE",
				BrowserType.EDGE
			));
	}

}