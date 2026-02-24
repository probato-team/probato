package org.probato.test.suite;

import org.probato.api.SQL;
import org.probato.api.Suite;

@SQL(
	datasource = "not-found", 
	scriptPath = { "data/sql/file.sql", "data/sql/file2.sql" })
@Suite(
	code = "UC15", 
	name = "Suite 15", 
	description = "This a simple test")
public class UC15_SuiteWithDatasourceNotFound {

}