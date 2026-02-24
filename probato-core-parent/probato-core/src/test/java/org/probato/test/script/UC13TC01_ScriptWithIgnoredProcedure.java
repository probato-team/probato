package org.probato.test.script;

import org.probato.api.Ignore;
import org.probato.api.Procedure;
import org.probato.api.Run;
import org.probato.api.Script;
import org.probato.test.procedure.ProcedureIgnoredRun;
import org.probato.type.Complexity;
import org.probato.type.Flow;
import org.probato.type.Relevance;

@Script(
	code = "UC13TC01",
	name = "Test case 01",
	description = "This a simple test",
	flow = Flow.ALTERNATIVE,
	complexity = Complexity.MEDIUM,
	relevance = Relevance.MEDIUM)
public class UC13TC01_ScriptWithIgnoredProcedure {

	@Ignore
	@Run
	public void run() {}

	@Procedure
	private ProcedureIgnoredRun procedure;

}