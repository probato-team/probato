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
import org.probato.test.suite.UC01_Suite;
import org.probato.test.suite.UC02_SuiteWithDatasetNoPaths;
import org.probato.test.suite.UC03_SuiteWithDatasetEmptyPath;
import org.probato.test.suite.UC04_SuiteWithDatasetFileNotFound;
import org.probato.test.suite.UC09_SuiteIgnored;
import org.probato.test.suite.UC12_SuiteWithIgnoredScript;

@DisplayName("Test -> DatasetComponentValidator")
class DatasetComponentValidatorTest {
	
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

		validators.forEach(validator -> validator.execute(UC09_SuiteIgnored.class));

		assertEquals(1, validators.size());
	}

	@Test
	@DisplayName("Should ignore validate script successfully")
	void shouldIgnoreValidateScriptSuccessfully() {

		var validators = ComponentValidator.getInstance(ComponentValidatorType.DATASET);

		validators.forEach(validator -> validator.execute(UC12_SuiteWithIgnoredScript.class));

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
						UC02_SuiteWithDatasetNoPaths.class,
						"List of dataset files must have at least 1 item in the @Dataset annotation: 'org.probato.test.script.UC02TC01_ScriptDatasetNoPaths'"),
				Arguments.of(
						UC03_SuiteWithDatasetEmptyPath.class,
						"Dataset path must be required in the @Dataset annotation: 'org.probato.test.script.UC03TC01_ScriptDatasetEmptyPath'"),
				Arguments.of(
						UC04_SuiteWithDatasetFileNotFound.class,
						"Dataset file 'path/to/file-not-found.csv' not found: 'org.probato.test.script.UC04TC01_ScriptDatasetFileNotFound'")				);
	}

}