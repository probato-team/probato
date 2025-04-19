package org.probato.test.script;

import org.probato.api.Dataset;
import org.probato.api.Procedure;
import org.probato.api.Script;
import org.probato.entity.type.Complexity;
import org.probato.entity.type.Flow;
import org.probato.entity.type.Relevance;
import org.probato.test.procedure.ProcedureInvalidParam;

@Dataset("path/to/file.csv")
@Script(
	code = "UC11TC01", 
	name = "Test case 01", 
	description = "This a simple test", 
	flow = Flow.ALTERNATIVE, 
	complexity = Complexity.AVERAGE, 
	relevance = Relevance.AVERAGE)
public class UC11TC01_ScriptWithInvalidProcedureMethod {
	
	@Procedure
	private ProcedureInvalidParam procedure	;
	
	@Procedure
	void procedure(String text) {}
	
}