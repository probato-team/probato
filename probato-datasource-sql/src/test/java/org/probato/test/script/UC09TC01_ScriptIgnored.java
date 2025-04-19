package org.probato.test.script;

import org.probato.api.Ignore;
import org.probato.api.Run;
import org.probato.api.Script;
import org.probato.entity.type.Complexity;
import org.probato.entity.type.Flow;
import org.probato.entity.type.Relevance;

@Ignore
@Script(
	code = "UC02TC02", 
	name = "Test case 02", 
	description = "This a simple test", 
	flow = Flow.ALTERNATIVE, 
	complexity = Complexity.AVERAGE, 
	relevance = Relevance.AVERAGE)
public class UC09TC01_ScriptIgnored {
	
	@Ignore
	@Run
	public void run() {}
	
}