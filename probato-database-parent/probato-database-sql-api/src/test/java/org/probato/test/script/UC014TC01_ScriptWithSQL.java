package org.probato.test.script;

import org.probato.api.Procedure;
import org.probato.api.SQL;
import org.probato.api.SQLs;
import org.probato.api.Script;
import org.probato.type.Complexity;
import org.probato.type.Flow;
import org.probato.type.Relevance;

@SQLs(
	@SQL(
		datasource = "probato",
		scriptPath = { "data/sql/file.sql", "data/sql/file2.sql" }))
@Script(
	code = "UC14TC01",
	name = "Test case 01",
	description = "This a simple test",
	flow = Flow.MAIN,
	complexity = Complexity.MEDIUM,
	relevance = Relevance.MEDIUM)
public class UC014TC01_ScriptWithSQL {

	@Procedure
	void procedure() {}

}