package org.probato.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.MongodConfig;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import de.flapdoodle.embed.mongo.config.Net;

@DisplayName("UT - MongoDbUtils")
class MongoDbUtilsTest {

	private static final String URI = "mongodb://localhost:27017";
	private static final String DATABASE = "testdb";

	private static MongodExecutable mongodExecutable;
	private static MongodProcess mongodProcess;

	@BeforeAll
	static void setup() throws Exception {

		var starter = MongodStarter.getDefaultInstance();
		int port = 27017;

		var mongodConfig = MongodConfig.builder()
				.version(Version.Main.V6_0)
				.net(new Net(port, Network.localhostIsIPv6()))
				.build();

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

		MongoDbUtils.validateConnection(URI, DATABASE);

		assertTrue(Boolean.TRUE);
	}

	@Test
	@DisplayName("Should validate documents successfully")
	void shouldValidateDocumentsSuccessfully() throws IOException {

		var docs = MongoDbUtils.getDocuments("data/nosql/mongo/file.json");

		MongoDbUtils.validateDocuments(URI, DATABASE, docs);

		assertTrue(Boolean.TRUE);
	}

	@Test
	@DisplayName("Should execute documents successfully")
	void shouldExecuteDocumentsSuccessfully() throws IOException {

		var docs = MongoDbUtils.getDocuments("data/nosql/mongo/file.json");

		MongoDbUtils.executeDocuments(URI, DATABASE, docs);

		assertTrue(Boolean.TRUE);
	}

}