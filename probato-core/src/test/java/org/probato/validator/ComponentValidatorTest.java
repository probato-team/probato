package org.probato.validator;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.probato.test.suite.UC01_Suite;

@DisplayName("Test - ComponentValidator")
class ComponentValidatorTest {

	@Test
	@DisplayName("Should validate successfully")
	void shouldValidateSuccessfully() {
		
		var service = ValidationService.getInstance();
		
		service.execute(UC01_Suite.class);
		
		assertTrue(Boolean.TRUE);
	}

}