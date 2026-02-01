package org.probato.test.script;

import org.probato.api.Page;
import org.probato.api.Procedure;
import org.probato.api.Script;
import org.probato.test.procedure.ProcedureInvalidParam;
import org.probato.test.procedure.ProcedureWithInvalidPage;
import org.probato.type.Complexity;
import org.probato.type.Flow;
import org.probato.type.Relevance;

@Script(
	code = "UC10TC01",
	name = "Test case 01",
	description = "This a simple test",
	flow = Flow.ALTERNATIVE,
	complexity = Complexity.MEDIUM,
	relevance = Relevance.MEDIUM)
public class UC14TC01_ScriptWithInvalidProcedure {

	@Page
	private ProcedureWithInvalidPage page;

	@Procedure
	private ProcedureInvalidParam procedure	;

}