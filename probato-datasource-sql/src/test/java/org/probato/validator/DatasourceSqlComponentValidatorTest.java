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
import org.probato.test.suite.UC01_SuiteWithSQL;
import org.probato.test.suite.UC11_SuiteWithDatasourceNotFound;
import org.probato.test.suite.UC12_SuiteWithoutDatasourceName;
import org.probato.test.suite.UC13_SuiteWithoutUrl;
import org.probato.test.suite.UC14_SuiteWithoutDriver;
import org.probato.test.suite.UC15_SuiteWithoutUsername;
import org.probato.test.suite.UC16_SuiteWithoutPassword;
import org.probato.test.suite.UC17_SuiteDatasourceDriverNotFound;

@DisplayName("Test -> DatasourceComponentValidator")
class DatasourceSqlComponentValidatorTest {

	@Test
	@DisplayName("Should execute validator successfully")
	void shouldExecuteValidadorSuccessfully() {

		var validators = ComponentValidator.getInstance(ComponentValidatorType.DATASOURCE)
				.stream()
				.filter(DatasourceSqlComponentValidator.class::isInstance)
				.collect(Collectors.toList());

		validators.forEach(validator -> validator.execute(UC01_SuiteWithSQL.class));

		assertEquals(1, validators.size());
	}

	@Test
	@DisplayName("Should ignore validate suite successfully")
	void shouldIgnoreValidateSuiteSuccessfully() {

		var validators = ComponentValidator.getInstance(ComponentValidatorType.DATASOURCE)
				.stream()
				.filter(DatasourceSqlComponentValidator.class::isInstance)
				.collect(Collectors.toList());

		validators.forEach(validator -> validator.execute(UC03_SuiteIgnored.class));

		assertEquals(1, validators.size());
	}

	@Test
	@DisplayName("Should ignore validate script successfully")
	void shouldIgnoreValidateScriptSuccessfully() {

		var validators = ComponentValidator.getInstance(ComponentValidatorType.DATASOURCE)
				.stream()
				.filter(DatasourceSqlComponentValidator.class::isInstance)
				.collect(Collectors.toList());

		validators.forEach(validator -> validator.execute(UC04_SuiteWithIgnoredScript.class));

		assertEquals(1, validators.size());
	}

	@ParameterizedTest
	@MethodSource("getInvalidDatasource")
	@DisplayName("Should validate datasource")
	void shouldValidateDatasource(Class<?> suiteClazz, String message) {

		var validators = ComponentValidator.getInstance(ComponentValidatorType.DATASOURCE)
				.stream()
				.filter(DatasourceSqlComponentValidator.class::isInstance)
				.collect(Collectors.toList());

		var exception = assertThrows(IntegrityException.class,
				() -> validators.forEach(validator -> validator.execute(suiteClazz)));

		assertEquals(message, exception.getMessage());
	}

	private static Stream<Arguments> getInvalidDatasource() {
		return Stream.of(
				Arguments.of(
						UC11_SuiteWithDatasourceNotFound.class,
						"Datasource 'not-found' not fount"),
				Arguments.of(
						UC12_SuiteWithoutDatasourceName.class,
						"Datasource name must be required in the @SQL annotation: 'org.probato.test.suite.UC12_SuiteWithoutDatasourceName'"),
				Arguments.of(
						UC13_SuiteWithoutUrl.class,
						"Datasource 'without-url.url' must be required in the @SQL annotation: 'org.probato.test.suite.UC13_SuiteWithoutUrl'"),
				Arguments.of(
						UC14_SuiteWithoutDriver.class,
						"Datasource 'without-driver.driver' must be required in the @SQL annotation: 'org.probato.test.suite.UC14_SuiteWithoutDriver'"),
				Arguments.of(
						UC15_SuiteWithoutUsername.class,
						"Datasource 'without-username.username' must be required in the @SQL annotation: 'org.probato.test.suite.UC15_SuiteWithoutUsername'"),
				Arguments.of(
						UC16_SuiteWithoutPassword.class,
						"Datasource 'without-password.password' must be required in the @SQL annotation: 'org.probato.test.suite.UC16_SuiteWithoutPassword'"),
				Arguments.of(
						UC17_SuiteDatasourceDriverNotFound.class,
						"Datasource 'driver-not-found' Invalid connection validation: 'org.not.found.Driver'"));
	}

}