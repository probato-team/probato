package org.probato.test.script;

import org.probato.api.Procedure;
import org.probato.api.SQL;
import org.probato.api.SQLs;
import org.probato.api.Script;
import org.probato.entity.type.Complexity;
import org.probato.entity.type.Flow;
import org.probato.entity.type.Relevance;
import org.probato.test.procedure.ProcedureRun;
import org.probato.test.procedure.ProcedureRunWithParam;

@SQLs(
	@SQL(
		datasource = "probato",
		scriptPath = { "data/sql/file.sql", "data/sql/file2.sql" }))
@Script(
	code = "UC01TC01",
	name = "Test case 01",
	description = "Test case 01",
	flow = Flow.MAIN,
	complexity = Complexity.AVERAGE,
	relevance = Relevance.AVERAGE)
public class UC01TC01_ScriptWithSQL {

	@Procedure
	private ProcedureRun run;

	@Procedure
	private ProcedureRunWithParam runWithParam;

	@Procedure
	void procedure() {}

}