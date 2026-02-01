package org.probato.loader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.probato.test.script.UC10TC02_ScriptWithSql;
import org.probato.test.suite.UC10_SuiteWithSql;

@DisplayName("UT - AnnotationLoader")
class SqlLoaderTest {

	@ParameterizedTest
	@MethodSource("getClazzSql")
	@DisplayName("Should get sql filepath successfully")
	void shouldHasSqlSuccessfully(Class<?> clazz) {

		var result = SqlLoader.hasSql(clazz);

		assertEquals(Boolean.TRUE, result);
	}

	@Test
	@DisplayName("Should get sql filepath successfully")
	void shouldHasNoSqlSuccessfully() {

		var result = SqlLoader.hasSql(SqlLoaderTest.class);

		assertEquals(Boolean.FALSE, result);
	}

	@ParameterizedTest
	@MethodSource("getClazzSql")
	@DisplayName("Should get sql filepath successfully")
	void shouldGetSqlFilespathSuccessfully(Class<?> clazz) {

		var sqls = SqlLoader.getSqlPaths(clazz);

		assertNotNull(sqls);
		assertEquals(1, sqls.size());
	}

	private static Stream<Arguments> getClazzSql() {
		return Stream.of(
				Arguments.of(UC10_SuiteWithSql.class),
				Arguments.of(UC10TC02_ScriptWithSql.class));
	}

}