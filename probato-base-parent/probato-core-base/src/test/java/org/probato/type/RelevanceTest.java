package org.probato.type;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayName("UT - Relevance type")
class RelevanceTest {

	@ParameterizedTest
	@MethodSource("getParams")
	@DisplayName("Should conveter value successfully")
	void shouldConverterValueSuccessfully(Relevance item, int expected) {

		var value = item.getCoefficient();

		assertAll("Validate value",
				() -> assertEquals(expected, value));
	}

	private static Stream<Arguments> getParams() {
		return Stream.of(
			Arguments.of(
				Relevance.VERY_LOW,
				2
			),
			Arguments.of(
				Relevance.LOW,
				4
			),
			Arguments.of(
				Relevance.MEDIUM,
				8
			),
			Arguments.of(
				Relevance.HIGH,
				16
			),
			Arguments.of(
				Relevance.VERY_HIGH,
				32
			));
	}

}