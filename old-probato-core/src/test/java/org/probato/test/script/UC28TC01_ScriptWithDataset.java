package org.probato.test.script;

import org.probato.api.Dataset;
import org.probato.api.Procedure;
import org.probato.api.Script;
import org.probato.model.type.Complexity;
import org.probato.model.type.Flow;
import org.probato.model.type.Relevance;
import org.probato.test.procedure.ProcedureRun;
import org.probato.test.procedure.ProcedureRunWithParam;

@Dataset("path/to/file.csv")
@Script(
	code = "UC14TC01", 
	name = "Test case 01", 
	description = "This a simple test", 
	flow = Flow.MAIN, 
	complexity = Complexity.AVERAGE, 
	relevance = Relevance.AVERAGE)
public class UC28TC01_ScriptWithDataset {

	@Procedure
	private ProcedureRun run;

	@Procedure
	private ProcedureRunWithParam runWithParam;

	@Procedure
	void procedure() {}

}