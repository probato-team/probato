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
import org.probato.type.ComponentValidatorType;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.MongodConfig;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.config.Timeout;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;

import org.probato.test.suite.UC09_SuiteIgnored;
import org.probato.test.suite.UC12_SuiteWithIgnoredScript;
import org.probato.test.suite.UC13_SuiteWithIgnoredProcedure;
import org.probato.test.suite.UC14_SuiteWithMongoNoSQL;
import org.probato.test.suite.UC24_SuiteWithMongoNoSQLEmptyPath;
import org.probato.test.suite.UC25_SuiteWithMongoNoSQLBlankPath;
import org.probato.test.suite.UC26_SuiteWithMongoNoSQLNotFound;

@DisplayName("UT - MongoDbNoSqlComponentValidator")
class MongoDbNoSqlComponentValidatorTest {

	private static MongodExecutable mongodExecutable;
	private static MongodProcess mongodProcess;

	@BeforeAll
	static void setup() throws Exception {

		var starter = MongodStarter.getDefaultInstance();
		int port = 27017;

		var mongodConfig = MongodConfig.builder()
				.version(Version.Main.V6_0)
				.timeout(new Timeout(2000))
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