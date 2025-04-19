package org.probato.test.script;

import org.probato.api.Script;
import org.probato.entity.type.Complexity;
import org.probato.entity.type.Flow;
import org.probato.entity.type.Relevance;

@Script(
	code = "UC0000000000000000000000000000000000000003", 
	name = "Test case 03", 
	description = "This a simple test", 
	flow = Flow.ALTERNATIVE, 
	complexity = Complexity.AVERAGE, 
	relevance = Relevance.AVERAGE)
public class UC03TC01_ScriptIdMaxLength {}