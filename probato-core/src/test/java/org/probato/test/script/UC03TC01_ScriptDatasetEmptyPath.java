package org.probato.test.script;

import org.probato.api.Dataset;
import org.probato.api.Script;
import org.probato.model.type.Complexity;
import org.probato.model.type.Flow;
import org.probato.model.type.Relevance;

@Dataset(value = {""})
@Script(
	code = "UC03TC01", 
	name = "Test case 01", 
	description = "This a simple test", 
	flow = Flow.MAIN, 
	complexity = Complexity.AVERAGE, 
	relevance = Relevance.AVERAGE)
public class UC03TC01_ScriptDatasetEmptyPath {}