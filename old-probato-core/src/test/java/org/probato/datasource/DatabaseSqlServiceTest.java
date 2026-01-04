package org.probato.datasource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.Collectors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.probato.exception.ImpeditiveException;
import org.probato.test.suite.UC14_SuiteWithSQL;
import org.probato.test.suite.UC21_SuiteDatasourceDriverNotFound;

@DisplayName("Test -> DatabaseSqlService")
class DatabaseSqlServiceTest {
	
	@Test
	@DisplayName("Should run database sql successfully")
	void shouldRunDatabaseSqlSuccessfully() {

		var services = DatasourceService.getInstance()
				.stream()
				.collect(Collectors.toList());

		services.forEach(service -> service.run(UC14_SuiteWithSQL.class));

		assertEquals(1, services.size());
	}
	
	@Test
	@DisplayName("Should run database sql failure")
	void shouldRunDatabaseSqlFailure() {
		
		var services = DatasourceService.getInstance()
				.stream()
				.collect(Collectors.toList());
		
		var exception = assertThrows(ImpeditiveException.class, 
				() -> services.forEach(service -> service.run(UC21_SuiteDatasourceDriverNotFound.class)));
		
		assertEquals("An error occurred while executing SQL command:'data/sql/file.sql' \norg.not.found.Driver", exception.getMessage());
	}

}