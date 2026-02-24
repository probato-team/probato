package org.probato.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.probato.exception.IntegrityException;

@DisplayName("UT - ValidationService")
class ValidationServiceTest {

	@Test
	@DisplayName("Should validate when not validator service implement")
	void shouldValidateNotExistValidatorImplement() {

		var exception = assertThrows(IntegrityException.class, ValidationService::get);

		assertEquals("Component validator implementation not found", exception.getMessage());
	}

}
