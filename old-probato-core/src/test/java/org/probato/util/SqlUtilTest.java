package org.probato.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Test -> SqlUtil")
class SqlUtilTest {

	@Test
	@DisplayName("Should file sql valid successfully")
	void shouldFileSqlValidSuccessfully() {

		var value = SqlUtil.isValidFile("data/sql/test-file.sql");

		assertTrue(value);
	}

	@Test
	@DisplayName("Should file sql valid successfully")
	void shouldGetFileSqlSuccessfully() {

		var value = SqlUtil.getSqlFiles("data/sql/test-file.sql");

		assertNotNull(value);
		assertEquals(1, value.length);
	}

	@Test
	@DisplayName("Should get queries commands successfully")
	void shouldGetQueriesCommandSuccessfully() throws IOException {

		var value = SqlUtil.getQueries("data/sql/test-file.sql");

		assertNotNull(value);
		assertEquals(1, value.size());
		assertEquals("SELECT 1 FROM test t", value.get(0));
	}

	@Test
	@DisplayName("Should validate connection successfully")
	void shouldValidateConnectionSuccessfully() throws Exception {

		var url = "jdbc:h2:mem:testdb";
		var driver = "org.h2.Driver";
		var username = "sa";
		var password = "password";

		SqlUtil.validateConnection(url, username, password, driver, null);

		assertTrue(Boolean.TRUE);
	}

	@Test
	@DisplayName("Should validate queries command successfully")
	void shouldValidateQueriesSuccessfully() throws Exception {

		var queries = List.of("SELECT 1");

		var url = "jdbc:h2:mem:testdb";
		var driver = "org.h2.Driver";
		var username = "sa";
		var password = "password";

		SqlUtil.validateQueries(url, username, password, driver, null, queries);

		assertTrue(Boolean.TRUE);
	}

	@Test
	@DisplayName("Should execute queries command successfully")
	void shouldExecuteQueriesSuccessfully() throws Exception {

		var queries = List.of("SELECT 1");

		var url = "jdbc:h2:mem:testdb";
		var driver = "org.h2.Driver";
		var username = "sa";
		var password = "password";

		SqlUtil.executeQueries(url, username, password, driver, null, queries);

		assertTrue(Boolean.TRUE);
	}

}