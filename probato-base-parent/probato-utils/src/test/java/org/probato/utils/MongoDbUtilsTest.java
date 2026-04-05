package org.probato.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.probato.test.util.IgnoreIfWorkflow;

@IgnoreIfWorkflow
@DisplayName("UT - MongoDbUtils")
class MongoDbUtilsTest {

	private static final String URI = "mongodb://localhost:27017/authSource=admin";
	private static final String USERNAME = "admin";
	private static final String PASSWORD = "secret";
	private static final String DATABASE = "admin";

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
				URI,
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
				URI,
				DATABASE,
				USERNAME,
				PASSWORD,
				docs);

		assertTrue(Boolean.TRUE);
	}

	@Disabled
	@Test
	@DisplayName("Should execute documents successfully")
	void shouldExecuteDocumentsSuccessfully() throws IOException {

		var docs = MongoDbUtils.getDocuments("data/nosql/mongo/file.json");

		MongoDbUtils.executeDocuments(
				URI,
				DATABASE,
				USERNAME,
				PASSWORD,
				docs);

		assertTrue(Boolean.TRUE);
	}

}