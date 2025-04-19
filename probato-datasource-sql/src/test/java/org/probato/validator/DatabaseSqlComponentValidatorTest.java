package org.probato.validator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.probato.entity.type.ComponentValidatorType;
import org.probato.exception.IntegrityException;
import org.probato.test.suite.UC03_SuiteIgnored;
import org.probato.test.suite.UC04_SuiteWithIgnoredScript;
import org.probato.test.suite.UC05_SuiteWithIgnoredProcedure;
import org.probato.test.suite.UC01_SuiteWithSQL;
import org.probato.test.suite.UC07_SuiteWithSQLEmptyPath;
import org.probato.test.suite.UC08_SuiteWithSQLBlankPath;
import org.probato.test.suite.UC09_SuiteWithSQLNotFound;

@DisplayName("Test -> DatabaseSqlComponentValidator")
class DatabaseSqlComponentValidatorTest {

	@Test
	@DisplayName("Should execute validator successfully")
	void shouldExecuteValidadorSuccessfully() {

		var validators = ComponentValidator.getInstance(ComponentValidatorType.DATASOURCE)
				.stream()
				.filter(DatabaseSqlComponentValidator.class::isInstance)
				.collect(Collectors.toList());

		validators.forEach(validator -> validator.execute(UC01_SuiteWithSQL.class));

		assertEquals(1, validators.size());
	}

	@Test
	@DisplayName("Should ignore validate suite successfully")
	void shouldIgnoreValidateSuiteSuccessfully() {

		var validators = ComponentValidator.getInstance(ComponentValidatorType.DATASOURCE)
				.stream()
				.filter(DatabaseSqlComponentValidator.class::isInstance)
				.collect(Collectors.toList());

		validators.forEach(validator -> validator.execute(UC03_SuiteIgnored.class));

		assertEquals(1, validators.size());
	}

	@Test
	@DisplayName("Should ignore validate script successfully")
	void shouldIgnoreValidateScriptSuccessfully() {

		var validators = ComponentValidator.getInstance(ComponentValidatorType.DATASOURCE)
				.stream()
				.filter(DatabaseSqlComponentValidator.class::isInstance)
				.collect(Collectors.toList());

		validators.forEach(validator -> validator.execute(UC04_SuiteWithIgnoredScript.class));

		assertEquals(1, validators.size());
	}

	@Test
	@DisplayName("Should ignore validate procedure class successfully")
	void shouldIgnoreValidateProcedureClassSuccessfully() {

		var validators = ComponentValidator.getInstance(ComponentValidatorType.DATASOURCE)
				.stream()
				.filter(DatabaseSqlComponentValidator.class::isInstance)
				.collect(Collectors.toList());

		validators.forEach(validator -> validator.execute(UC05_SuiteWithIgnoredProcedure.class));

		assertEquals(1, validators.size());
	}

	@ParameterizedTest
	@MethodSource("getInvalidSql")
	@DisplayName("Should validate procedure class data")
	void shouldValidateSql(Class<?> suiteClazz, String message) {

		var validators = ComponentValidator.getInstance(ComponentValidatorType.DATASOURCE)
				.stream()
				.filter(DatabaseSqlComponentValidator.class::isInstance)
				.collect(Collectors.toList());

		var exception = assertThrows(IntegrityException.class,
				() -> validators.forEach(validator -> validator.execute(suiteClazz)));

		assertEquals(message, exception.getMessage());
	}

	private static Stream<Arguments> getInvalidSql() {
		return Stream.of(
				Arguments.of(
						UC07_SuiteWithSQLEmptyPath.class,
						"List of sql must have at least 1 item in the @SQL annotation: 'org.probato.test.suite.UC07_SuiteWithSQLEmptyPath'"),
				Arguments.of(
						UC08_SuiteWithSQLBlankPath.class,
						"List of sql must not have null or empty value in the @SQL annotation: 'org.probato.test.suite.UC08_SuiteWithSQLBlankPath'"),
				Arguments.of(
						UC09_SuiteWithSQLNotFound.class,
						"SQL file 'path/to/file-not-found.sql' not found: 'org.probato.test.suite.UC09_SuiteWithSQLNotFound'")

				// TODO validate sql syntax only
				/*
				Arguments.of(
						UC10_SuiteWithInvalidSQL.class,
						"Invalid SQL command in file 'data/sql/invalid-file.sql': \n'Syntax error in SQL statement \"SELECT ONE INVALID [*]COMMAND SQL\"; SQL statement:\nSELECT ONE INVALID COMMAND SQL [42000-232]'"),
				Arguments.of(
						UC21_SuiteDatasourceDriverNotFound.class,
						"Problem when trying to load SQL file: 'org.not.found.Driver'")
						*/
				);
	}

}