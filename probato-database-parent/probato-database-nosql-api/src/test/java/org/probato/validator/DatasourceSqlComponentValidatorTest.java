package org.probato.validator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
import org.probato.test.suite.UC14_SuiteWithNoSQL;
import org.probato.test.suite.UC15_SuiteWithDatasourceNotFound;
import org.probato.test.suite.UC17_SuiteWithoutUrl;
import org.probato.test.suite.UC19_SuiteWithoutDatabase;
import org.probato.type.ComponentValidatorType;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.MongodConfig;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;

@DisplayName("UT - DatasourceNoSqlComponentValidator")
class DatasourceSqlComponentValidatorTest {

	private static MongodExecutable mongodExecutable;
	private static MongodProcess mongodProcess;

	@BeforeAll
	static void setup() throws Exception {

		var starter = MongodStarter.getDefaultInstance();
		int port = 27017;

		var mongodConfig = MongodConfig.builder()
				.version(Version.Main.V6_0)
				.net(new Net(port, Network.localhostIsIPv6())).build();

		mongodExecutable = starter.prepare(mongodConfig);
		mongodProcess = mongodExecutable.start();
	}

	@AfterAll
	static void teardown() {
		if (mongodProcess != null)
			mongodProcess.stop();
		if (mongodExecutable != null)
			mongodExecutable.stop();
	}

	@Test
	@DisplayName("Should execute validator successfully")
	void shouldExecuteValidadorSuccessfully() {

		var validators = ComponentValidator.getInstance(ComponentValidatorType.DATASOURCE)
				.stream()
				.filter(DatasourceNoSqlComponentValidator.class::isInstance)
				.collect(Collectors.toList());

		validators.forEach(validator -> validator.execute(UC14_SuiteWithNoSQL.class));

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
						UC19_SuiteWithoutDatabase.class,
						"Datasource 'without-database.database' must be required in the @NoSQL annotation: 'org.probato.test.suite.UC19_SuiteWithoutDatabase'"));
	}

}