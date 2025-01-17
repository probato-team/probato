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
import org.probato.exception.IntegrityException;
import org.probato.model.type.ComponentValidatorType;
import org.probato.test.suite.UC09_SuiteIgnored;
import org.probato.test.suite.UC12_SuiteWithIgnoredScript;
import org.probato.test.suite.UC13_SuiteWithIgnoredProcedure;
import org.probato.test.suite.UC14_SuiteWithSQL;
import org.probato.test.suite.UC21_SuiteDatasourceDriverNotFound;
import org.probato.test.suite.UC24_SuiteWithSQLEmptyPath;
import org.probato.test.suite.UC25_SuiteWithSQLBlankPath;
import org.probato.test.suite.UC26_SuiteWithSQLNotFound;
import org.probato.test.suite.UC27_SuiteWithInvalidSQL;

@DisplayName("Test -> DatabaseSqlComponentValidator")
class DatabaseSqlComponentValidatorTest {
	
	@Test
	@DisplayName("Should execute validator successfully")
	void shouldExecuteValidadorSuccessfully() {

		var validators = ComponentValidator.getInstance(ComponentValidatorType.DATASOURCE)
				.stream()
				.filter(DatabaseSqlComponentValidator.class::isInstance)
				.collect(Collectors.toList());

		validators.forEach(validator -> validator.execute(UC14_SuiteWithSQL.class));

		assertEquals(1, validators.size());
	}
	
	@Test
	@DisplayName("Should ignore validate suite successfully")
	void shouldIgnoreValidateSuiteSuccessfully() {

		var validators = ComponentValidator.getInstance(ComponentValidatorType.DATASOURCE)
				.stream()
				.filter(DatabaseSqlComponentValidator.class::isInstance)
				.collect(Collectors.toList());

		validators.forEach(validator -> validator.execute(UC09_SuiteIgnored.class));

		assertEquals(1, validators.size());
	}

	@Test
	@DisplayName("Should ignore validate script successfully")
	void shouldIgnoreValidateScriptSuccessfully() {

		var validators = ComponentValidator.getInstance(ComponentValidatorType.DATASOURCE)
				.stream()
				.filter(DatabaseSqlComponentValidator.class::isInstance)
				.collect(Collectors.toList());

		validators.forEach(validator -> validator.execute(UC12_SuiteWithIgnoredScript.class));

		assertEquals(1, validators.size());
	}

	@Test
	@DisplayName("Should ignore validate procedure class successfully")
	void shouldIgnoreValidateProcedureClassSuccessfully() {

		var validators = ComponentValidator.getInstance(ComponentValidatorType.DATASOURCE)
				.stream()
				.filter(DatabaseSqlComponentValidator.class::isInstance)
				.collect(Collectors.toList());

		validators.forEach(validator -> validator.execute(UC13_SuiteWithIgnoredProcedure.class));

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
						UC24_SuiteWithSQLEmptyPath.class,
						"List of sql must have at least 1 item in the @SQL annotation: 'org.probato.test.suite.UC24_SuiteWithSQLEmptyPath'"),
				Arguments.of(
						UC25_SuiteWithSQLBlankPath.class,
						"List of sql must not have null or empty value in the @SQL annotation: 'org.probato.test.suite.UC25_SuiteWithSQLBlankPath'"),
				Arguments.of(
						UC26_SuiteWithSQLNotFound.class,
						"SQL file 'path/to/file-not-found.sql' not found: 'org.probato.test.suite.UC26_SuiteWithSQLNotFound'")
				
				// TODO validate sql syntax only
				/*
				Arguments.of(
						UC27_SuiteWithInvalidSQL.class,
						"Invalid SQL command in file 'data/sql/invalid-file.sql': \n'Syntax error in SQL statement \"SELECT ONE INVALID [*]COMMAND SQL\"; SQL statement:\nSELECT ONE INVALID COMMAND SQL [42000-232]'"),
				Arguments.of(
						UC21_SuiteDatasourceDriverNotFound.class,
						"Problem when trying to load SQL file: 'org.not.found.Driver'")
						*/
				);
	}

}