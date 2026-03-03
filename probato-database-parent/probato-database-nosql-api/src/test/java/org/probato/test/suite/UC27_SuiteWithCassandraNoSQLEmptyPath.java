package org.probato.test.suite;

import org.probato.api.NoSQL;
import org.probato.api.Suite;

@NoSQL(
	datasource = "probato-cassandra",
	scriptPath = {})
@Suite(
	code = "UC24",
	name = "Suite 24",
	description = "This a simple test")
public class UC27_SuiteWithCassandraNoSQLEmptyPath {

}