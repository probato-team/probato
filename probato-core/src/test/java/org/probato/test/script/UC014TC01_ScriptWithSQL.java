package org.probato.test.script;

import org.probato.api.Procedure;
import org.probato.api.SQL;
import org.probato.api.SQLs;
import org.probato.api.Script;
import org.probato.model.type.Complexity;
import org.probato.model.type.Flow;
import org.probato.model.type.Relevance;
import org.probato.test.procedure.ProcedureRun;
import org.probato.test.procedure.ProcedureRunWithParam;

@SQLs(
	@SQL(
		datasource = "probato", 
		scriptPath = { "path/to/file.sql", "path/to/file2.sql" }))
@Script(
	code = "UC14TC01", 
	name = "Test case 01", 
	description = "This a simple test", 
	flow = Flow.MAIN, 
	complexity = Complexity.AVERAGE, 
	relevance = Relevance.AVERAGE)
public class UC014TC01_ScriptWithSQL {

	@Procedure
	private ProcedureRun run;

	@Procedure
	private ProcedureRunWithParam runWithParam;

	@Procedure
	void procedure() {}

}