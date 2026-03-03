package org.probato.validator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.time.Duration;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.probato.exception.IntegrityException;
import org.probato.test.suite.UC09_SuiteIgnored;
import org.probato.test.suite.UC12_SuiteWithIgnoredScript;
import org.probato.test.suite.UC13_SuiteWithIgnoredProcedure;
import org.probato.test.suite.UC16_SuiteWithCassandraNoSQL;
import org.probato.test.suite.UC27_SuiteWithCassandraNoSQLEmptyPath;
import org.probato.test.suite.UC28_SuiteWithCassandraNoSQLBlankPath;
import org.probato.test.suite.UC29_SuiteWithCassandraNoSQLNotFound;
import org.probato.test.support.DockerSupport;
import org.probato.type.ComponentValidatorType;
import org.testcontainers.containers.CassandraContainer;

import com.datastax.oss.driver.api.core.CqlSession;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;

@DisplayName("UT - CassandraNoSqlComponentValidator")
class CassandraNoSqlComponentValidatorTest {

	private static final String KEYSPACE = "testks";

	private static CassandraContainer<?> cassandra;

	@SuppressWarnings("resource")
	@BeforeAll
	static void setup() {

		assumeTrue(
				DockerSupport.isDockerAvailable(),
				"Docker not available - skipping Testcontainers tests");

		cassandra = new CassandraContainer<>("cassandra:4.1")
				.withStartupTimeout(Duration.ofMinutes(2))
				.withCreateContainerCmdModifier(cmd -> cmd.getHostConfig()
						.withPortBindings(new PortBinding(
								Ports.Binding.bindPort(9042),
								new ExposedPort(9042))));

		cassandra.start();
	}

	@BeforeEach
	void beforeEach() {

		try (var session = CqlSession.builder()
				.addContactPoint(cassandra.getContactPoint())
	            .withLocalDatacenter(cassandra.getLocalDatacenter())
				.build()) {

			session.execute("DROP KEYSPACE IF EXISTS " + KEYSPACE);

			session.execute(String.format(
					"CREATE KEYSPACE %s WITH replication = "
					+ "{'class':'SimpleStrategy','replication_factor':1}",
					KEYSPACE));
		}
	}

	@AfterAll
	static void teardown() {
		if (cassandra != null) {
			cassandra.stop();
		}
	}

	@Test
	@DisplayName("Should execute validator successfully")
	void shouldExecuteValidadorSuccessfully() {

		var validators = ComponentValidator.getInstance(ComponentValidatorType.DATASOURCE)
				.stream()
				.filter(ComponentValidator.class::isInstance)
				.collect(Collectors.toList());

		validators.forEach(validator -> validator.execute(UC16_SuiteWithCassandraNoSQL.class));

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
						UC27_SuiteWithCassandraNoSQLEmptyPath.class,
						"List of nosql files must have at least 1 item in the @NoSQL annotation: 'org.probato.test.suite.UC27_SuiteWithCassandraNoSQLEmptyPath'"),
				Arguments.of(
						UC28_SuiteWithCassandraNoSQLBlankPath.class,
						"List of nosql files must not have null or empty value in the @NoSQL annotation: 'org.probato.test.suite.UC28_SuiteWithCassandraNoSQLBlankPath'"),
				Arguments.of(
						UC29_SuiteWithCassandraNoSQLNotFound.class,
						"NoSQL file 'path/to/file-not-found.json' not found: 'org.probato.test.suite.UC29_SuiteWithCassandraNoSQLNotFound'"));
	}

}