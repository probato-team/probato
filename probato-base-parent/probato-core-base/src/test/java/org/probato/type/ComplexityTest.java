package org.probato.type;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayName("UT - Complexity type")
class ComplexityTest {

	@ParameterizedTest
	@MethodSource("getParams")
	@DisplayName("Should conveter value successfully")
	void shouldConverterValueSuccessfully(Complexity item, int expected) {

		var value = item.getCoefficient();

		assertAll("Validate value",
				() -> assertEquals(expected, value));
	}

	private static Stream<Arguments> getParams() {
		return Stream.of(
			Arguments.of(
				Complexity.VERY_LOW,
				2
			),
			Arguments.of(
				Complexity.LOW,
				4
			),
			Arguments.of(
				Complexity.MEDIUM,
				8
			),
			Arguments.of(
				Complexity.HIGH,
				16
			),
			Arguments.of(
				Complexity.VERY_HIGH,
				32
			));
	}

}