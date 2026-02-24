package org.probato.test.script;

import org.probato.api.Script;
import org.probato.type.Complexity;
import org.probato.type.Flow;
import org.probato.type.Relevance;

@Script(
	code = "UC0000000000000000000000000000000000000003",
	name = "Test case 03",
	description = "This a simple test",
	flow = Flow.ALTERNATIVE,
	complexity = Complexity.MEDIUM,
	relevance = Relevance.MEDIUM)
public class UC03TC01_ScriptIdMaxLength {}