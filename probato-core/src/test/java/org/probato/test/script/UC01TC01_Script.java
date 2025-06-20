package org.probato.test.script;

import org.probato.api.Procedure;
import org.probato.api.Script;
import org.probato.model.type.Complexity;
import org.probato.model.type.Flow;
import org.probato.model.type.Relevance;
import org.probato.test.procedure.ProcedureRun;

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
	void procedure() {
		// Procedure implements
	}

}