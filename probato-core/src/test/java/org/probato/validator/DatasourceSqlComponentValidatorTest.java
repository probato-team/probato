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
import org.probato.test.suite.UC14_SuiteWithSQL;
import org.probato.test.suite.UC15_SuiteWithDatasourceNotFound;
import org.probato.test.suite.UC16_SuiteWithoutDatasourceName;
import org.probato.test.suite.UC17_SuiteWithoutUrl;
import org.probato.test.suite.UC18_SuiteWithoutDriver;
import org.probato.test.suite.UC19_SuiteWithoutUsername;
import org.probato.test.suite.UC20_SuiteWithoutPassword;
import org.probato.test.suite.UC21_SuiteDatasourceDriverNotFound;

@DisplayName("Test - DatasourceComponentValidator")
class DatasourceSqlComponentValidatorTest {
	
	@Test
	@DisplayName("Should execute validator successfully")
	void shouldExecuteValidadorSuccessfully() {
		
		var validators = ComponentValidator.getInstance(ComponentValidatorType.DATASOURCE)
				.stream()
				.filter(DatasourceSqlComponentValidator.class::isInstance)
				.collect(Collectors.toList());

		validators.forEach(validator -> validator.execute(UC14_SuiteWithSQL.class));

		assertEquals(1, validators.size());
	}
	
	@Test
	@DisplayName("Should ignore validate suite successfully")
	void shouldIgnoreValidateSuiteSuccessfully() {

		var validators = ComponentValidator.getInstance(ComponentValidatorType.DATASOURCE)
				.stream()
				.filter(DatasourceSqlComponentValidator.class::isInstance)
				.collect(Collectors.toList());

		validators.forEach(validator -> validator.execute(UC09_SuiteIgnored.class));

		assertEquals(1, validators.size());
	}

	@Test
	@DisplayName("Should ignore validate script successfully")
	void shouldIgnoreValidateScriptSuccessfully() {

		var validators = ComponentValidator.getInstance(ComponentValidatorType.DATASOURCE)
				.stream()
				.filter(DatasourceSqlComponentValidator.class::isInstance)
				.collect(Collectors.toList());

		validators.forEach(validator -> validator.execute(UC12_SuiteWithIgnoredScript.class));

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
						UC15_SuiteWithDatasourceNotFound.class,
						"Datasource 'not-found' not fount"),
				Arguments.of(
						UC16_SuiteWithoutDatasourceName.class,
						"Datasource name must be required in the @SQL annotation: 'org.probato.test.suite.UC16_SuiteWithoutDatasourceName'"),
				Arguments.of(
						UC17_SuiteWithoutUrl.class,
						"Datasource 'without-url.url' must be required in the @SQL annotation: 'org.probato.test.suite.UC17_SuiteWithoutUrl'"),
				Arguments.of(
						UC18_SuiteWithoutDriver.class,
						"Datasource 'without-driver.driver' must be required in the @SQL annotation: 'org.probato.test.suite.UC18_SuiteWithoutDriver'"),
				Arguments.of(
						UC19_SuiteWithoutUsername.class,
						"Datasource 'without-username.username' must be required in the @SQL annotation: 'org.probato.test.suite.UC19_SuiteWithoutUsername'"),
				Arguments.of(
						UC20_SuiteWithoutPassword.class,
						"Datasource 'without-password.password' must be required in the @SQL annotation: 'org.probato.test.suite.UC20_SuiteWithoutPassword'"),
				Arguments.of(
						UC21_SuiteDatasourceDriverNotFound.class,
						"Datasource 'driver-not-found' Invalid connection validation: 'org.not.found.Driver'"));
	}

}