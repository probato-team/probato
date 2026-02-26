package org.probato.database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Comparator;
import java.util.ServiceLoader;
import java.util.ServiceLoader.Provider;
import java.util.stream.Collectors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.probato.exception.IntegrityException;
import org.probato.test.suite.UC14_SuiteWithSQL;
import org.probato.test.suite.UC21_SuiteDatasourceDriverNotFound;

@DisplayName("UT -> DatabaseSqlProvider")
class DatabaseSqlProviderTest {

	@Test
	@DisplayName("Should run database sql successfully")
	void shouldRunDatabaseSqlSuccessfully() {

		var services = ServiceLoader.load(SqlProvider.class)
				.stream()
				.map(Provider::get)
				.sorted(Comparator.comparing(serviceClazz -> serviceClazz.getClass().getPackageName().equals(SqlProvider.class.getClass().getPackageName()), Comparator.reverseOrder()))
				.collect(Collectors.toList());

		services.forEach(service -> service.run(UC14_SuiteWithSQL.class));

		assertEquals(1, services.size());
	}

	@Test
	@DisplayName("Should run database sql failure")
	void shouldRunDatabaseSqlFailure() {

		var services = ServiceLoader.load(SqlProvider.class)
				.stream()
				.map(Provider::get)
				.sorted(Comparator.comparing(serviceClazz -> serviceClazz.getClass().getPackageName().equals(SqlProvider.class.getClass().getPackageName()), Comparator.reverseOrder()))
				.collect(Collectors.toList());

		var exception = assertThrows(IntegrityException.class,
				() -> services.forEach(service -> service.run(UC21_SuiteDatasourceDriverNotFound.class)));

		assertEquals("An error occurred while executing SQL command: 'data/sql/file.sql' org.not.found.Driver", exception.getMessage());
	}

}