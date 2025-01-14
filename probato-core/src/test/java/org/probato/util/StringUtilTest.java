package org.probato.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Test -> StringUtil")
class StringUtilTest {

	@Test
	@DisplayName("Should repleace values successfully")
	void shouldRepleaceValuesSuccessfully() {

		var value = StringUtil.replaceParam("{{EXAMPLE}}", "EXAMPLE", "value");

		assertEquals("value", value);
	}

	@Test
	@DisplayName("Should check contains caracter successfully")
	void shouldCheckContainsCharacterSuccessfully() {

		var value = StringUtil.containsCharacter("{{EXAMPLE}}", "EXAMPLE");

		assertTrue(value);
	}

	@Test
	@DisplayName("Should check contains special caracter successfully")
	void shouldCheckContainsSpecialCharacterSuccessfully() {

		var value = StringUtil.containsSpecialCharacter("é");

		assertTrue(value);
	}

	@Test
	@DisplayName("Should check blank character successfully")
	void shouldCheckBlankCharacterSuccessfully() {

		var value = StringUtil.isBlank("");

		assertTrue(value);
	}
}