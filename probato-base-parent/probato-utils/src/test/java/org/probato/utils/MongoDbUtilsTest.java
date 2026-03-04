package org.probato.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.io.IOException;
import java.time.Duration;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.probato.test.support.DockerSupport;
import org.testcontainers.containers.GenericContainer;

@DisplayName("UT - MongoDbUtils")
class MongoDbUtilsTest {

	private static final String USERNAME = "admin";
	private static final String PASSWORD = "secret";
	private static final String DATABASE = "admin";

	private static GenericContainer<?> mongo;
	private static String uri = "mongodb://localhost:27017";

	@SuppressWarnings({ "resource", "rawtypes" })
	@BeforeAll
	static void setup() {

		assumeTrue(
				DockerSupport.isDockerAvailable(),
				"Docker not available - skipping Testcontainers tests");

		mongo = new GenericContainer("mongo:6.0")
				.withEnv("MONGO_INITDB_ROOT_USERNAME", USERNAME)
				.withEnv("MONGO_INITDB_ROOT_PASSWORD", PASSWORD)
				.withEnv("MONGO_INITDB_DATABASE", DATABASE)
				.withExposedPorts(27017)
				.withStartupTimeout(Duration.ofMinutes(2));

		mongo.start();
	}

	@AfterAll
	static void teardown() {
		if (mongo != null) {
			mongo.stop();
		}
	}

	@Test
	@DisplayName("Should file json valid successfully")
	void shouldFileJsonValidSuccessfully() {

		var value = MongoDbUtils.isValidFile("data/nosql/mongo/file.json");

		assertTrue(value);
	}

	@Test
	@DisplayName("Should get file json successfully")
	void shouldGetFileJsonSuccessfully() {

		var value = MongoDbUtils.getNoSqlFiles("data/nosql/mongo/file.json");

		assertNotNull(value);
		assertEquals(1, value.length);
	}

	@Test
	@DisplayName("Should get document commands successfully")
	void shouldGetDocumentsSuccessfully() throws IOException {

		var value = MongoDbUtils.getDocuments("data/nosql/mongo/file.json");

		assertNotNull(value);
		assertEquals(1, value.size());
		assertEquals("insertOne", value.get(0).getString("operation"));
	}

	@Test
	@DisplayName("Should validate connection successfully")
	void shouldValidateConnectionSuccessfully() {

		MongoDbUtils.validateConnection(
				uri,
				USERNAME,
				PASSWORD,
				DATABASE);

		assertTrue(Boolean.TRUE);
	}

	@Test
	@DisplayName("Should validate documents successfully")
	void shouldValidateDocumentsSuccessfully() throws IOException {

		var docs = MongoDbUtils.getDocuments("data/nosql/mongo/file.json");

		MongoDbUtils.validateDocuments(
				uri,
				DATABASE,
				USERNAME,
				PASSWORD,
				docs);

		assertTrue(Boolean.TRUE);
	}

	@Test
	@DisplayName("Should execute documents successfully")
	void shouldExecuteDocumentsSuccessfully() throws IOException {

		var docs = MongoDbUtils.getDocuments("data/nosql/mongo/file.json");

		MongoDbUtils.executeDocuments(
				uri,
				DATABASE,
				USERNAME,
				PASSWORD,
				docs);

		assertTrue(Boolean.TRUE);
	}

}