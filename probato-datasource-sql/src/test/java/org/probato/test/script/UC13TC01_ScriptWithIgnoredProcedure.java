package org.probato.test.script;

import org.probato.api.Ignore;
import org.probato.api.Procedure;
import org.probato.api.Run;
import org.probato.api.Script;
import org.probato.entity.type.Complexity;
import org.probato.entity.type.Flow;
import org.probato.entity.type.Relevance;
import org.probato.test.procedure.ProcedureIgnoredRun;

@Script(
	code = "UC13TC01", 
	name = "Test case 01", 
	description = "This a simple test", 
	flow = Flow.ALTERNATIVE, 
	complexity = Complexity.AVERAGE, 
	relevance = Relevance.AVERAGE)
public class UC13TC01_ScriptWithIgnoredProcedure {
	
	@Ignore
	@Run
	public void run() {}

	@Procedure
	private ProcedureIgnoredRun procedure;
	
}