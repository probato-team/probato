package org.probato.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Test -> StringUtil")
class StringUtilsTest {

	@Test
	@DisplayName("Should repleace values successfully")
	void shouldRepleaceValuesSuccessfully() {

		var value = StringUtils.replaceParam("{{EXAMPLE}}", "EXAMPLE", "value");

		assertEquals("value", value);
	}

	@Test
	@DisplayName("Should check contains caracter successfully")
	void shouldCheckContainsCharacterSuccessfully() {

		var value = StringUtils.containsCharacter("{{EXAMPLE}}", "EXAMPLE");

		assertTrue(value);
	}

	@Test
	@DisplayName("Should check contains special caracter successfully")
	void shouldCheckContainsSpecialCharacterSuccessfully() {

		var value = StringUtils.containsSpecialCharacter("é");

		assertTrue(value);
	}

	@Test
	@DisplayName("Should check blank character successfully")
	void shouldCheckBlankCharacterSuccessfully() {

		var value = StringUtils.isBlank("");

		assertTrue(value);
	}
}