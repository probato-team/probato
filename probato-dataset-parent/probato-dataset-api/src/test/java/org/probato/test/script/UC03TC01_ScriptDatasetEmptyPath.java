package org.probato.test.script;

import org.probato.api.Dataset;
import org.probato.api.Script;
import org.probato.type.Complexity;
import org.probato.type.Flow;
import org.probato.type.Relevance;

@Dataset("")
@Script(
	code = "UC03TC01",
	name = "Test case 01",
	description = "This a simple test",
	flow = Flow.MAIN,
	complexity = Complexity.MEDIUM,
	relevance = Relevance.MEDIUM)
public class UC03TC01_ScriptDatasetEmptyPath {}