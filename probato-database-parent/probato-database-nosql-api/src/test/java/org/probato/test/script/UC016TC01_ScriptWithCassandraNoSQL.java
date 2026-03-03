package org.probato.test.script;

import org.probato.api.NoSQL;
import org.probato.api.NoSQLs;
import org.probato.api.Procedure;
import org.probato.api.Script;
import org.probato.type.Complexity;
import org.probato.type.Flow;
import org.probato.type.Relevance;

@NoSQLs(
	@NoSQL(
		datasource = "probato-cassandra",
		scriptPath = { "data/nosql/cassandra/file.cql" }))
@Script(
	code = "UC16TC01",
	name = "Test case 16",
	description = "This a simple test",
	flow = Flow.MAIN,
	complexity = Complexity.MEDIUM,
	relevance = Relevance.MEDIUM)
public class UC016TC01_ScriptWithCassandraNoSQL {

	@Procedure
	void procedure() {}

}