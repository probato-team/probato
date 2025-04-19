package org.probato.test.script;

import org.probato.api.Dataset;
import org.probato.api.Procedure;
import org.probato.api.Script;
import org.probato.entity.type.Complexity;
import org.probato.entity.type.Flow;
import org.probato.entity.type.Relevance;
import org.probato.test.procedure.ProcedureRun;
import org.probato.test.procedure.ProcedureRunWithParam;

@Dataset("data/csv/file.csv")
@Script(
	code = "UC01TC01", 
	name = "Test case 01", 
	description = "This a simple test", 
	flow = Flow.MAIN, 
	complexity = Complexity.AVERAGE, 
	relevance = Relevance.AVERAGE)
public class UC01TC01_Script {

	@Procedure
	private ProcedureRun run;

	@Procedure
	private ProcedureRunWithParam runWithParam;

	@Procedure
	void procedure() {
		// Implementation procedure
	}

}