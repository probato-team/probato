package org.probato.utils;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.io.IOException;
import java.time.Duration;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.probato.test.datamodel.DockerSupport;
import org.testcontainers.containers.CassandraContainer;

import com.datastax.oss.driver.api.core.CqlSession;

@DisplayName("UT - CassandraUtils")
class CassandraUtilsTest {

	private static final String KEYSPACE = "testks";

	private static CassandraContainer<?> cassandra;

	@BeforeAll
	static void beforeAll() {

		assumeTrue(
				DockerSupport.isDockerAvailable(),
	            "Docker not available - skipping Testcontainers tests");

		cassandra = new CassandraContainer<>("cassandra:4.1")
				.withStartupTimeout(Duration.ofMinutes(2));

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
	static void afterEach() {
		if (cassandra != null) {
			cassandra.stop();
		}
	}

	@Test
	@DisplayName("Should file json valid successfully")
	void shouldFileJsonValidSuccessfully() {

		var value = CassandraUtils.isValidFile("data/nosql/cassandra/file.cql");

		assertTrue(value);
	}

	@Test
	@DisplayName("Should get file json successfully")
	void shouldGetFileJsonSuccessfully() {

		var value = CassandraUtils.getCqlFiles("data/nosql/cassandra/file.cql");

		assertNotNull(value);
		assertEquals(1, value.length);
	}

	@Test
	@DisplayName("Should get document commands successfully")
	void shouldGetDocumentsSuccessfully() throws IOException {

		var value = CassandraUtils.getCommands("data/nosql/cassandra/file.cql");

		assertNotNull(value);
		assertFalse(value.isEmpty());
		assertTrue(value.get(0).toUpperCase().contains("CREATE TABLE"));
	}

	@Test
	@DisplayName("Should validate connection successfully")
	void shouldValidateConnectionSuccessfully() {

		CassandraUtils.validateConnection(
				cassandra.getHost(),
				cassandra.getFirstMappedPort());

		assertTrue(Boolean.TRUE);
	}

	@Test
	@DisplayName("Should validate documents successfully")
	void shouldValidateDocumentsSuccessfully() throws IOException {

		var commands = CassandraUtils.getCommands("data/nosql/cassandra/file.cql");

		CassandraUtils.validateCommands(
				cassandra.getHost(),
				cassandra.getFirstMappedPort(),
				KEYSPACE,
				commands);

		assertTrue(Boolean.TRUE);
	}

	@Test
	@DisplayName("Should execute documents successfully")
	void shouldExecuteDocumentsSuccessfully() throws IOException {

		var commands = CassandraUtils.getCommands("data/nosql/cassandra/file.cql");

		CassandraUtils.executeCommands(
				cassandra.getHost(),
				cassandra.getFirstMappedPort(),
				KEYSPACE,
				commands);

		assertTrue(Boolean.TRUE);
	}

}