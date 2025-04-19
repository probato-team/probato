package org.probato.test.suite;

import org.probato.api.SQL;
import org.probato.api.Suite;

@SQL(
	datasource = "probato",
	scriptPath = { "path/to/file-not-found.sql" })
@Suite(
	code = "UC09",
	name = "Suite 09",
	description = "This a simple test")
public class UC09_SuiteWithSQLNotFound {

}