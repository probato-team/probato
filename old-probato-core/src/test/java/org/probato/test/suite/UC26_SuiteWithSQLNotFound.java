package org.probato.test.suite;

import org.probato.api.SQL;
import org.probato.api.Suite;

@SQL(
	datasource = "probato", 
	scriptPath = { "path/to/file-not-found.sql" })
@Suite(
	code = "UC26", 
	name = "Suite 26", 
	description = "This a simple test")
public class UC26_SuiteWithSQLNotFound {

}