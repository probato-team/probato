package org.probato.test.suite;

import org.probato.api.NoSQL;
import org.probato.api.Suite;
import org.probato.api.TestCase;
import org.probato.test.script.UC016TC01_ScriptWithCassandraNoSQL;

@NoSQL(
	datasource = "probato-cassandra",
	scriptPath = { "data/nosql/cassandra/file.json" })
@Suite(
	code = "UC16",
	name = "Suite 16",
	description = "This a simple test")
public class UC16_SuiteWithCassandraNoSQL {

	@TestCase
	private UC016TC01_ScriptWithCassandraNoSQL tc01;

}