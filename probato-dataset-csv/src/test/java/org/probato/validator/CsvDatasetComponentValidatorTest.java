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
import org.probato.test.suite.UC01_Suite;
import org.probato.test.suite.UC02_SuiteIgnored;
import org.probato.test.suite.UC03_SuiteWithIgnoredScript;
import org.probato.test.suite.UC04_SuiteWithDatasetNoPaths;
import org.probato.test.suite.UC05_SuiteWithDatasetEmptyPath;
import org.probato.test.suite.UC06_SuiteWithDatasetFileNotFound;

@DisplayName("Test -> CsvDatasetComponentValidator")
class CsvDatasetComponentValidatorTest {

	@Test
	@DisplayName("Should execute validator successfully")
	void shouldExecuteValidadorSuccessfully() {

		var validators = ComponentValidator.getInstance(ComponentValidatorType.DATASET);

		validators.forEach(validator -> validator.execute(UC01_Suite.class));

		assertEquals(1, validators.size());
	}

	@Test
	@DisplayName("Should ignore validate suite successfully")
	void shouldIgnoreValidateSuiteSuccessfully() {

		var validators = ComponentValidator.getInstance(ComponentValidatorType.DATASET);

		validators.forEach(validator -> validator.execute(UC02_SuiteIgnored.class));

		assertEquals(1, validators.size());
	}

	@Test
	@DisplayName("Should ignore validate script successfully")
	void shouldIgnoreValidateScriptSuccessfully() {

		var validators = ComponentValidator.getInstance(ComponentValidatorType.DATASET);

		validators.forEach(validator -> validator.execute(UC03_SuiteWithIgnoredScript.class));

		assertEquals(1, validators.size());
	}

	@ParameterizedTest
	@MethodSource("getInvalidDatasource")
	@DisplayName("Should validate datasource")
	void shouldValidateDatasource(Class<?> suiteClazz, String message) {

		var validators = ComponentValidator.getInstance(ComponentValidatorType.DATASET);

		var exception = assertThrows(IntegrityException.class,
				() -> validators.forEach(validator -> validator.execute(suiteClazz)));

		assertEquals(message, exception.getMessage());
	}

	private static Stream<Arguments> getInvalidDatasource() {
		return Stream.of(
				Arguments.of(
						UC04_SuiteWithDatasetNoPaths.class,
						"List of dataset files must have at least 1 item in the @Dataset annotation: 'org.probato.test.script.UC04TC01_ScriptDatasetNoPaths'"),
				Arguments.of(
						UC05_SuiteWithDatasetEmptyPath.class,
						"Dataset path must be required in the @Dataset annotation: 'org.probato.test.script.UC05TC01_ScriptDatasetEmptyPath'"),
				Arguments.of(
						UC06_SuiteWithDatasetFileNotFound.class,
						"Dataset file 'path/to/file-not-found.csv' not found: 'org.probato.test.script.UC06TC01_ScriptDatasetFileNotFound'")				);
	}

}