package org.probato.type;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayName("UT - Screen type")
class ScreenTest {

	@ParameterizedTest
	@MethodSource("getParams")
	@DisplayName("Should conveter value successfully")
	void shouldConverterValueSuccessfully(String value, Screen expected) {

		var screen = Screen.fromString(value);

		assertEquals(expected, screen);
	}

	private static Stream<Arguments> getParams() {
		return Stream.of(
			Arguments.of(
				"PRIMARY",
				Screen.PRIMARY
			),
			Arguments.of(
				"SECONDARY",
				Screen.SECONDARY
			),
			Arguments.of(
				"OTHER",
				Screen.PRIMARY
			));
	}

}