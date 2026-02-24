package org.probato.test.script;

import org.probato.api.Script;
import org.probato.model.type.Complexity;
import org.probato.model.type.Flow;
import org.probato.model.type.Relevance;

@Script(
	code = "", 
	name = "Test case 02", 
	description = "This a simple test", 
	flow = Flow.ALTERNATIVE, 
	complexity = Complexity.AVERAGE, 
	relevance = Relevance.AVERAGE)
public class UC02TC01_ScriptIdMinLength {}