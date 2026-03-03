package org.probato.validator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.probato.exception.IntegrityException;
import org.probato.test.suite.UC09_SuiteIgnored;
import org.probato.test.suite.UC12_SuiteWithIgnoredScript;
import org.probato.test.suite.UC13_SuiteWithIgnoredProcedure;
import org.probato.test.suite.UC14_SuiteWithMongoNoSQL;
import org.probato.test.suite.UC24_SuiteWithMongoNoSQLEmptyPath;
import org.probato.test.suite.UC25_SuiteWithMongoNoSQLBlankPath;
import org.probato.test.suite.UC26_SuiteWithMongoNoSQLNotFound;
import org.probato.test.support.DockerSupport;
import org.probato.type.ComponentValidatorType;
import org.testcontainers.containers.MongoDBContainer;

import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import com.mongodb.client.MongoClients;

@DisplayName("UT - MongoDbNoSqlComponentValidator")
class MongoDbNoSqlComponentValidatorTest {

	private static final String DATABASE = "testdb";

	private static MongoDBContainer mongo;

	@BeforeAll
	static void setup() {

		assumeTrue(
				DockerSupport.isDockerAvailable(),
				"Docker not available - skipping Testcontainers tests");

		mongo = new MongoDBContainer("mongo:6.0")
				.withExposedPorts(27017)
				.withCreateContainerCmdModifier(cmd -> cmd.withHostConfig(cmd.getHostConfig()
						.withPortBindings(new PortBinding(
								Ports.Binding.bindPort(27017),
								new ExposedPort(27017)))));

		mongo.start();

		try (var client = MongoClients.create(mongo.getConnectionString())) {
			client.getDatabase(DATABASE).listCollectionNames().first();
		}
	}

	@AfterAll
	static void teardown() {
		if (mongo != null) {
			mongo.stop();
		}
	}

	@Test
	@DisplayName("Should execute validator successfully")
	void shouldExecuteValidadorSuccessfully() {

		var validators = ComponentValidator.getInstance(ComponentValidatorType.DATASOURCE)
				.stream()
				.filter(ComponentValidator.class::isInstance)
				.collect(Collectors.toList());

		validators.forEach(validator -> validator.execute(UC14_SuiteWithMongoNoSQL.class));

		assertEquals(3, validators.size());
	}

	@Test
	@DisplayName("Should ignore validate suite successfully")
	void shouldIgnoreValidateSuiteSuccessfully() {

		var validators = ComponentValidator.getInstance(ComponentValidatorType.DATASOURCE)
				.stream()
				.filter(ComponentValidator.class::isInstance)
				.collect(Collectors.toList());

		validators.forEach(validator -> validator.execute(UC09_SuiteIgnored.class));

		assertEquals(3, validators.size());
	}

	@Test
	@DisplayName("Should ignore validate script successfully")
	void shouldIgnoreValidateScriptSuccessfully() {

		var validators = ComponentValidator.getInstance(ComponentValidatorType.DATASOURCE)
				.stream()
				.filter(ComponentValidator.class::isInstance)
				.collect(Collectors.toList());

		validators.forEach(validator -> validator.execute(UC12_SuiteWithIgnoredScript.class));

		assertEquals(3, validators.size());
	}

	@Test
	@DisplayName("Should ignore validate procedure class successfully")
	void shouldIgnoreValidateProcedureClassSuccessfully() {

		var validators = ComponentValidator.getInstance(ComponentValidatorType.DATASOURCE)
				.stream()
				.filter(ComponentValidator.class::isInstance)
				.collect(Collectors.toList());

		validators.forEach(validator -> validator.execute(UC13_SuiteWithIgnoredProcedure.class));

		assertEquals(3, validators.size());
	}

	@ParameterizedTest
	@MethodSource("getInvalidSql")
	@DisplayName("Should validate procedure class data")
	void shouldValidateSql(Class<?> suiteClazz, String message) {

		var validators = ComponentValidator.getInstance(ComponentValidatorType.DATASOURCE)
				.stream()
				.filter(ComponentValidator.class::isInstance)
				.collect(Collectors.toList());

		var exception = assertThrows(IntegrityException.class,
				() -> validators.forEach(validator -> validator.execute(suiteClazz)));

		assertEquals(message, exception.getMessage());
	}

	private static Stream<Arguments> getInvalidSql() {
		return Stream.of(
				Arguments.of(
						UC24_SuiteWithMongoNoSQLEmptyPath.class,
						"List of nosql files must have at least 1 item in the @NoSQL annotation: 'org.probato.test.suite.UC24_SuiteWithMongoNoSQLEmptyPath'"),
				Arguments.of(
						UC25_SuiteWithMongoNoSQLBlankPath.class,
						"List of nosql files must not have null or empty value in the @NoSQL annotation: 'org.probato.test.suite.UC25_SuiteWithMongoNoSQLBlankPath'"),
				Arguments.of(
						UC26_SuiteWithMongoNoSQLNotFound.class,
						"NoSQL file 'path/to/file-not-found.json' not found: 'org.probato.test.suite.UC26_SuiteWithMongoNoSQLNotFound'"));
	}

}