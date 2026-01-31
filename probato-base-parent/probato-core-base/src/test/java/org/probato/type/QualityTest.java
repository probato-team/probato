package org.probato.type;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayName("UT - Quality type")
class QualityTest {

	@ParameterizedTest
	@MethodSource("getParams")
	@DisplayName("Should conveter value successfully")
	void shouldConverterValueSuccessfully(Quality item, int expected) {

		var value = item.getDivisor();

		assertAll("Validate value",
				() -> assertEquals(expected, value));
	}

	private static Stream<Arguments> getParams() {
		return Stream.of(
			Arguments.of(
				Quality.HIGH,
				1
			),
			Arguments.of(
				Quality.MEDIUM,
				2
			),
			Arguments.of(
				Quality.LOW,
				4
			));
	}

}