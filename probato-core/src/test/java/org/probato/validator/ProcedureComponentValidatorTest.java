package org.probato.validator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.probato.entity.type.ComponentValidatorType;
import org.probato.exception.IntegrityException;
import org.probato.test.suite.UC00_NoSuite;
import org.probato.test.suite.UC01_Suite;
import org.probato.test.suite.UC09_SuiteIgnored;
import org.probato.test.suite.UC10_SuiteWithInvalidProcedure;
import org.probato.test.suite.UC11_SuiteWithInvalidProcedureMethod;
import org.probato.test.suite.UC12_SuiteWithIgnoredScript;
import org.probato.test.suite.UC13_SuiteWithIgnoredProcedure;

@DisplayName("Test -> ProcedureComponentValidator")
class ProcedureComponentValidatorTest {
	
	@Test
	@DisplayName("Should execute validator successfully")
	void shouldExecuteValidadorSuccessfully() {

		var validators = ComponentValidator.getInstance(ComponentValidatorType.PROCEDURE);

		validators.forEach(validator -> validator.execute(UC01_Suite.class));

		assertEquals(1, validators.size());
	}
	
	@Test
	@DisplayName("Should ignore validate suite successfully")
	void shouldIgnoreValidateSuiteSuccessfully() {

		var validators = ComponentValidator.getInstance(ComponentValidatorType.PROCEDURE);

		validators.forEach(validator -> validator.execute(UC09_SuiteIgnored.class));

		assertEquals(1, validators.size());
	}

	@Test
	@DisplayName("Should ignore validate script successfully")
	void shouldIgnoreValidateScriptSuccessfully() {

		var validators = ComponentValidator.getInstance(ComponentValidatorType.PROCEDURE);

		validators.forEach(validator -> validator.execute(UC12_SuiteWithIgnoredScript.class));

		assertEquals(1, validators.size());
	}

	@Test
	@DisplayName("Should ignore validate procedure class successfully")
	void shouldIgnoreValidateProcedureClassSuccessfully() {

		var validators = ComponentValidator.getInstance(ComponentValidatorType.PROCEDURE);

		validators.forEach(validator -> validator.execute(UC13_SuiteWithIgnoredProcedure.class));

		assertEquals(1, validators.size());
	}

	@ParameterizedTest
	@MethodSource("getInvalidProcedureClassData")
	@DisplayName("Should validate procedure class data")
	void shouldValidateProcedureClassData(Class<?> suiteClazz, String message) {

		var validators = ComponentValidator.getInstance(ComponentValidatorType.PROCEDURE);

		var exception = assertThrows(IntegrityException.class,
				() -> validators.forEach(validator -> validator.execute(suiteClazz)));

		assertEquals(message, exception.getMessage());
	}

	private static Stream<Arguments> getInvalidProcedureClassData() {
		return Stream.of(
				Arguments.of(
						UC00_NoSuite.class,
						"Procedure must have method annotated with @Run: 'org.probato.test.procedure.ProcedureNoRun'"),
				Arguments.of(
						UC10_SuiteWithInvalidProcedure.class,
						"Test procedure method can only have a parameter if there is a Dataset for script: 'org.probato.test.procedure.ProcedureInvalidParam.run'"),
				Arguments.of(
						UC11_SuiteWithInvalidProcedureMethod.class,
						"Method must receive parameters of the DataModel type only: 'org.probato.test.procedure.ProcedureInvalidParam.run'"));
	}

}