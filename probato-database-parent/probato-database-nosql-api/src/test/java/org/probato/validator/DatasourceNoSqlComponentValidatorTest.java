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
import org.probato.test.suite.UC14_SuiteWithMongoNoSQL;
import org.probato.test.suite.UC15_SuiteWithDatasourceNotFound;
import org.probato.test.suite.UC17_SuiteWithoutUrl;
import org.probato.test.suite.UC18_SuiteWithoutType;
import org.probato.test.suite.UC19_SuiteWithoutDatabase;
import org.probato.test.support.DockerSupport;
import org.probato.type.ComponentValidatorType;
import org.testcontainers.containers.MongoDBContainer;

import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import com.mongodb.client.MongoClients;

@DisplayName("UT - DatasourceNoSqlComponentValidator")
class DatasourceNoSqlComponentValidatorTest {

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
				.filter(DatasourceNoSqlComponentValidator.class::isInstance)
				.collect(Collectors.toList());

		validators.forEach(validator -> validator.execute(UC14_SuiteWithMongoNoSQL.class));

		assertEquals(1, validators.size());
	}

	@Test
	@DisplayName("Should ignore validate suite successfully")
	void shouldIgnoreValidateSuiteSuccessfully() {

		var validators = ComponentValidator.getInstance(ComponentValidatorType.DATASOURCE)
				.stream()
				.filter(DatasourceNoSqlComponentValidator.class::isInstance)
				.collect(Collectors.toList());

		validators.forEach(validator -> validator.execute(UC09_SuiteIgnored.class));

		assertEquals(1, validators.size());
	}

	@Test
	@DisplayName("Should ignore validate script successfully")
	void shouldIgnoreValidateScriptSuccessfully() {

		var validators = ComponentValidator.getInstance(ComponentValidatorType.DATASOURCE)
				.stream()
				.filter(DatasourceNoSqlComponentValidator.class::isInstance)
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
				.filter(DatasourceNoSqlComponentValidator.class::isInstance)
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
						UC17_SuiteWithoutUrl.class,
						"Datasource 'without-url.url' must be required in the @NoSQL annotation: 'org.probato.test.suite.UC17_SuiteWithoutUrl'"),
				Arguments.of(
						UC18_SuiteWithoutType.class,
						"Datasource 'without-type.type' must be required in the @NoSQL annotation: 'org.probato.test.suite.UC18_SuiteWithoutType'"),
				Arguments.of(
						UC19_SuiteWithoutDatabase.class,
						"Datasource 'without-database.database' must be required in the @NoSQL annotation: 'org.probato.test.suite.UC19_SuiteWithoutDatabase'"));
	}

}