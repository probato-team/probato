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
		datasource = "probato-mongo",
		scriptPath = { "data/nosql/mongo/file.json" }))
@Script(
	code = "UC14TC01",
	name = "Test case 01",
	description = "This a simple test",
	flow = Flow.MAIN,
	complexity = Complexity.MEDIUM,
	relevance = Relevance.MEDIUM)
public class UC014TC01_ScriptWithMongoNoSQL {

	@Procedure
	void procedure() {}

}