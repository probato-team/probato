package org.probato.test.script;

import org.probato.api.Page;
import org.probato.api.Procedure;
import org.probato.api.Script;
import org.probato.model.type.Complexity;
import org.probato.model.type.Flow;
import org.probato.model.type.Relevance;
import org.probato.test.procedure.ProcedureInvalidParam;
import org.probato.test.procedure.ProcedureWithInvalidPage;

@Script(
	code = "UC10TC01", 
	name = "Test case 01", 
	description = "This a simple test", 
	flow = Flow.ALTERNATIVE, 
	complexity = Complexity.AVERAGE, 
	relevance = Relevance.AVERAGE)
public class UC10TC01_ScriptWithInvalidProcedure {
	
	@Page
	private ProcedureWithInvalidPage page;
	
	@Procedure
	private ProcedureInvalidParam procedure	;
	
}