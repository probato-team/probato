package org.probato.test.script;

import org.probato.api.Script;
import org.probato.entity.type.Complexity;
import org.probato.entity.type.Flow;
import org.probato.entity.type.Relevance;

@Script(
	code = "ÚÇ04-TÇ01", 
	name = "Test case 04", 
	description = "This a simple test", 
	flow = Flow.ALTERNATIVE, 
	complexity = Complexity.AVERAGE,
	relevance = Relevance.AVERAGE)
public class UC04TC01_ScriptIdMaxLength {}