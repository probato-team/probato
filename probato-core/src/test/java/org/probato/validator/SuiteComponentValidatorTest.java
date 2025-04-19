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
import org.probato.test.suite.UC02_SuiteIdMinLenght;
import org.probato.test.suite.UC03_SuiteIdMaxLenght;
import org.probato.test.suite.UC04_SuiteIdSpecialCharacter;
import org.probato.test.suite.UC05_SuiteNameMinLenght;
import org.probato.test.suite.UC06_SuiteNameMaxLenght;
import org.probato.test.suite.UC07_SuiteDescriptionMaxLenght;
import org.probato.test.suite.UC08_SuiteEmptyTestCase;
import org.probato.test.suite.UC09_SuiteIgnored;

@DisplayName("Test -> SuiteComponentValidator")
class SuiteComponentValidatorTest {
	
	@Test
	@DisplayName("Should execute validator successfully")
	void shouldExecuteValidatorSuccessfully() {

		var validators = ComponentValidator.getInstance(ComponentValidatorType.SUITE);

		validators.forEach(validator -> validator.execute(UC01_Suite.class));

		assertEquals(1, validators.size());
	}
	
	@Test
	@DisplayName("Should ignore validate suite successfully")
	void shouldIgnoreValidateSuiteSuccessfully() {

		var validators = ComponentValidator.getInstance(ComponentValidatorType.SUITE);

		validators.forEach(validator -> validator.execute(UC09_SuiteIgnored.class));

		assertEquals(1, validators.size());
	}

	@ParameterizedTest
	@MethodSource("getInvalidSuiteData")
	@DisplayName("Should validate suite data")
	void shouldValidateSuiteData(Class<?> suiteClazz, String message) {

		var validators = ComponentValidator.getInstance(ComponentValidatorType.SUITE);

		var exception = assertThrows(IntegrityException.class,
				() -> validators.forEach(validator -> validator.execute(suiteClazz)));

		assertEquals(message, exception.getMessage());
	}

	private static Stream<Arguments> getInvalidSuiteData() {
		return Stream.of(
				Arguments.of(
						UC00_NoSuite.class,
						"Class must be annotated with `@Suite`: 'org.probato.test.suite.UC00_NoSuite'"),
				Arguments.of(
						UC02_SuiteIdMinLenght.class,
						"Suite ID must be between 1 and 20 characters long: 'org.probato.test.suite.UC02_SuiteIdMinLenght'"),
				Arguments.of(
						UC03_SuiteIdMaxLenght.class,
						"Suite ID must be between 1 and 20 characters long: 'org.probato.test.suite.UC03_SuiteIdMaxLenght'"),
				Arguments.of(
						UC04_SuiteIdSpecialCharacter.class,
						"Suite ID should contain only letters and numbers: 'org.probato.test.suite.UC04_SuiteIdSpecialCharacter'"),
				Arguments.of(
						UC05_SuiteNameMinLenght.class,
						"Suite name must be between 3 and 60 characters long: 'org.probato.test.suite.UC05_SuiteNameMinLenght'"),
				Arguments.of(
						UC06_SuiteNameMaxLenght.class,
						"Suite name must be between 3 and 60 characters long: 'org.probato.test.suite.UC06_SuiteNameMaxLenght'"),
				Arguments.of(
						UC07_SuiteDescriptionMaxLenght.class,
						"Suite description must not be more than 2000 characters in length: 'org.probato.test.suite.UC07_SuiteDescriptionMaxLenght'"),
				Arguments.of(
						UC08_SuiteEmptyTestCase.class,
						"Suite must have at least 1 test case: 'org.probato.test.suite.UC08_SuiteEmptyTestCase'"));
	}

}