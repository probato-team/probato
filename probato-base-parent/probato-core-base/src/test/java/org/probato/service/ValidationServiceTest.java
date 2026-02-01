package org.probato.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.probato.exception.IntegrityException;

@DisplayName("UT - ValidationService")
class ValidationServiceTest {

	@Test
	@DisplayName("Should execute validator service successfully")
	void shouldExecuteValidatorSuccessfully() {

		var exception = assertThrows(IntegrityException.class, ValidationService::getInstance);

		assertEquals("Component validator implementation not found", exception.getMessage());
	}

}
