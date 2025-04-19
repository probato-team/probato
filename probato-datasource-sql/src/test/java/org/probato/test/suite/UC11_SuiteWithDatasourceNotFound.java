package org.probato.test.suite;

import org.probato.api.SQL;
import org.probato.api.Suite;

@SQL(
	datasource = "not-found",
	scriptPath = { "data/sql/file.sql", "data/sql/file2.sql" })
@Suite(
	code = "UC11",
	name = "Suite 11",
	description = "This a simple test")
public class UC11_SuiteWithDatasourceNotFound {

}