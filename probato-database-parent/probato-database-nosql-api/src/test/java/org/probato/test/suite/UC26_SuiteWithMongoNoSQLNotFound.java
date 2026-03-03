package org.probato.test.suite;

import org.probato.api.NoSQL;
import org.probato.api.Suite;

@NoSQL(
	datasource = "probato-mongo",
	scriptPath = { "path/to/file-not-found.json" })
@Suite(
	code = "UC26",
	name = "Suite 26",
	description = "This a simple test")
public class UC26_SuiteWithMongoNoSQLNotFound {

}