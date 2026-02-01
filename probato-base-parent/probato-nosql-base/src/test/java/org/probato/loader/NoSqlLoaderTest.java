package org.probato.loader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.probato.test.script.UC02TC02_ScriptWithSql;
import org.probato.test.suite.UC01_SuiteWithSql;

@DisplayName("UT - NoSqlLoader")
class NoSqlLoaderTest {

	@ParameterizedTest
	@MethodSource("getClazzSql")
	@DisplayName("Should get sql filepath successfully")
	void shouldHasSqlSuccessfully(Class<?> clazz) {

		var result = NoSqlLoader.hasNoSql(clazz);

		assertEquals(Boolean.TRUE, result);
	}

	@Test
	@DisplayName("Should get sql filepath successfully")
	void shouldHasNoSqlSuccessfully() {

		var result = NoSqlLoader.hasNoSql(NoSqlLoaderTest.class);

		assertEquals(Boolean.FALSE, result);
	}

	@ParameterizedTest
	@MethodSource("getClazzSql")
	@DisplayName("Should get sql filepath successfully")
	void shouldGetSqlFilespathSuccessfully(Class<?> clazz) {

		var sqls = NoSqlLoader.getNoSqlPaths(clazz);

		assertNotNull(sqls);
		assertEquals(1, sqls.size());
	}

	private static Stream<Arguments> getClazzSql() {
		return Stream.of(
				Arguments.of(UC01_SuiteWithSql.class),
				Arguments.of(UC02TC02_ScriptWithSql.class));
	}

}