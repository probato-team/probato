package org.probato.test.script;

import org.probato.api.Script;
import org.probato.type.Complexity;
import org.probato.type.Flow;
import org.probato.type.Relevance;

@Script(
	code = "ÚÇ04-TÇ01",
	name = "Test case 04",
	description = "This a simple test",
	flow = Flow.ALTERNATIVE,
	complexity = Complexity.MEDIUM,
	relevance = Relevance.MEDIUM)
public class UC04TC01_ScriptIdMaxLength {}