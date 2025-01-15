package org.probato.validator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.probato.exception.IntegrityException;
import org.probato.model.type.ComponentValidatorType;
import org.probato.test.suite.UC00_NoSuite;
import org.probato.test.suite.UC01_Suite;
import org.probato.test.suite.UC02_SuiteIdMinLenght;
import org.probato.test.suite.UC03_SuiteIdMaxLenght;
import org.probato.test.suite.UC04_SuiteIdSpecialCharacter;
import org.probato.test.suite.UC05_SuiteNameMinLenght;
import org.probato.test.suite.UC06_SuiteNameMaxLenght;
import org.probato.test.suite.UC07_SuiteDescriptionMaxLenght;
import org.probato.test.suite.UC08_SuiteScriptEmptyTestCase;
import org.probato.test.suite.UC09_SuiteIgnored;
import org.probato.test.suite.UC12_SuiteWithIgnoredScript;

@DisplayName("Test - ScriptComponentValidator")
class ScriptComponentValidatorTest {

	@Test
	@DisplayName("Should execute validator successfully")
	void shouldExecuteValidatorSuccessfully() {

		var validators = ComponentValidator.getInstance(ComponentValidatorType.SCRIPT);
		
		validators.forEach(validator -> validator.execute(UC01_Suite.class));

		assertEquals(1, validators.size());
	}
	
	@Test
	@DisplayName("Should ignore validate suite successfully")
	void shouldIgnoreValidateSuiteSuccessfully() {

		var validators = ComponentValidator.getInstance(ComponentValidatorType.SCRIPT);

		validators.forEach(validator -> validator.execute(UC09_SuiteIgnored.class));

		assertEquals(1, validators.size());
	}

	@Test
	@DisplayName("Should ignore validate script successfully")
	void shouldIgnoreValidateScriptSuccessfully() {

		var validators = ComponentValidator.getInstance(ComponentValidatorType.SCRIPT);

		validators.forEach(validator -> validator.execute(UC12_SuiteWithIgnoredScript.class));

		assertEquals(1, validators.size());
	}

	@ParameterizedTest
	@MethodSource("getInvalidScriptData")
	@DisplayName("Should validate script data")
	void shouldValidateScriptData(Class<?> suiteClazz, String message) {

		var validators = ComponentValidator.getInstance(ComponentValidatorType.SCRIPT);

		var exception = assertThrows(IntegrityException.class,
				() -> validators.forEach(validator -> validator.execute(suiteClazz)));

		assertEquals(message, exception.getMessage());
	}

	private static Stream<Arguments> getInvalidScriptData() {
		return Stream.of(
				Arguments.of(
						UC00_NoSuite.class,
						"Class must be annotated with `@Script`: 'org.probato.test.script.UC00TC00_NoScript'"),
				Arguments.of(
						UC02_SuiteIdMinLenght.class,
						"Script ID must be between 3 and 40 characters long: 'org.probato.test.script.UC02TC01_ScriptIdMinLength'"),
				Arguments.of(
						UC03_SuiteIdMaxLenght.class,
						"Script ID must be between 3 and 40 characters long: 'org.probato.test.script.UC03TC01_ScriptIdMaxLength'"),
				Arguments.of(
						UC04_SuiteIdSpecialCharacter.class,
						"Script ID should contain only letters and numbers: 'org.probato.test.script.UC04TC01_ScriptIdMaxLength'"),
				Arguments.of(
						UC05_SuiteNameMinLenght.class,
						"Script name must be between 3 and 250 characters long: 'org.probato.test.script.UC05TC01_ScriptNameMinLength'"),
				Arguments.of(
						UC06_SuiteNameMaxLenght.class,
						"Script name must be between 3 and 250 characters long: 'org.probato.test.script.UC06TC01_ScriptNameMaxLength'"),
				Arguments.of(
						UC07_SuiteDescriptionMaxLenght.class,
						"Script description must not be more than 2.000 characters in length: 'org.probato.test.script.UC07TC01_ScriptDescriptionMaxLength'"),
				Arguments.of(
						UC08_SuiteScriptEmptyTestCase.class,
						"Script must have at least 1 procedure: 'org.probato.test.script.UC08TC01_ScriptEmptyProcedure'"));
	}

}