package org.probato.test.script;

import org.probato.api.Dataset;
import org.probato.api.Procedure;
import org.probato.api.Script;
import org.probato.test.procedure.ProcedureInvalidParam;
import org.probato.type.Complexity;
import org.probato.type.Flow;
import org.probato.type.Relevance;

@Dataset("path/to/file.csv")
@Script(
	code = "UC11TC01",
	name = "Test case 01",
	description = "This a simple test",
	flow = Flow.ALTERNATIVE,
	complexity = Complexity.MEDIUM,
	relevance = Relevance.MEDIUM)
public class UC15TC01_ScriptWithInvalidProcedureMethod {

	@Procedure
	private ProcedureInvalidParam procedure	;

	@Procedure
	void procedure(String text) {}

}