package org.probato.test.suite;

import org.probato.api.NoSQL;
import org.probato.api.Suite;

@NoSQL(
	datasource = "probato-cassandra",
	scriptPath = { "", "" })
@Suite(
	code = "UC25",
	name = "Suite 25",
	description = "This a simple test")
public class UC28_SuiteWithCassandraNoSQLBlankPath {

}