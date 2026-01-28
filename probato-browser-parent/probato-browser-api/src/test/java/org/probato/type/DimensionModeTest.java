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

@DisplayName("UT - Dimension mode type")
class DimensionModeTest {

	@ParameterizedTest
	@MethodSource("getParams")
	@DisplayName("Should conveter value successfully")
	void shouldConverterValueSuccessfully(String value, DimensionMode expected) {

		var dimensionMode = DimensionMode.fromString(value);

		assertAll("Validate value",
				() -> assertEquals(expected, dimensionMode),
				() -> assertEquals(expected.description(), dimensionMode.description()));
	}

	@Test
	@DisplayName("Should validate when invalid value")
	void shouldValidateInvalidValue() {

		var exception = assertThrows(IntegrityException.class, () -> DimensionMode.fromString("OTHER"));

		assertEquals("Invalid value for DimensionMode property: OTHER", exception.getMessage());
	}

	private static Stream<Arguments> getParams() {
		return Stream.of(
			Arguments.of(
				"FULLSCREEN",
				DimensionMode.FULLSCREEN
			),
			Arguments.of(
				"MAXIMIZED",
				DimensionMode.MAXIMIZED
			),
			Arguments.of(
				"CUSTOM",
				DimensionMode.CUSTOM
			));
	}

}