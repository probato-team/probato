package org.probato.database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.time.Duration;
import java.util.stream.Collectors;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.probato.test.suite.UC16_SuiteWithCassandraNoSQL;
import org.probato.test.support.DockerSupport;
import org.probato.type.DatasourceType;
import org.testcontainers.containers.CassandraContainer;

import com.datastax.oss.driver.api.core.CqlSession;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;

@DisplayName("UT - CassandraNoSqlProvider")
class CassandraNoSqlProviderTest {

	private static final String KEYSPACE = "testks";

	private static CassandraContainer<?> cassandra;

//	@SuppressWarnings("resource")
//	@BeforeAll
//	static void setup() {
//
//		assumeTrue(
//				DockerSupport.isDockerAvailable(),
//				"Docker not available - skipping Testcontainers tests");
//
//		cassandra = new CassandraContainer<>("cassandra:4.1")
//				.withStartupTimeout(Duration.ofMinutes(2))
//				.withCreateContainerCmdModifier(cmd -> cmd.getHostConfig()
//						.withPortBindings(new PortBinding(
//								Ports.Binding.bindPort(9042),
//								new ExposedPort(9042))));
//
//		cassandra.start();
//	}
//
//	@BeforeEach
//	void beforeEach() {
//
//		try (var session = CqlSession.builder()
//				.addContactPoint(cassandra.getContactPoint())
//	            .withLocalDatacenter(cassandra.getLocalDatacenter())
//				.build()) {
//
//			session.execute("DROP KEYSPACE IF EXISTS " + KEYSPACE);
//
//			session.execute(String.format(
//					"CREATE KEYSPACE %s WITH replication = "
//					+ "{'class':'SimpleStrategy','replication_factor':1}",
//					KEYSPACE));
//		}
//	}
//
//	@AfterAll
//	static void teardown() {
//		if (cassandra != null) {
//			cassandra.stop();
//		}
//	}

	@Test
	@DisplayName("Should execute validator successfully")
	void shouldExecuteValidadorSuccessfully() {

		var validators = NoSqlProvider.getInstance(DatasourceType.CASSANDRA)
				.stream()
				.filter(NoSqlProvider.class::isInstance)
				.collect(Collectors.toList());

		validators.forEach(validator -> validator.run(UC16_SuiteWithCassandraNoSQL.class));

		assertEquals(1, validators.size());
	}

}
