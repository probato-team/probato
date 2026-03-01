package org.probato.test.suite;

import org.probato.api.NoSQL;
import org.probato.api.Suite;

@NoSQL(
	datasource = "not-found",
	scriptPath = { "data/nosql/file-not-found.json" })
@Suite(
	code = "UC15",
	name = "Suite 15",
	description = "This a simple test")
public class UC15_SuiteWithDatasourceNotFound {

}