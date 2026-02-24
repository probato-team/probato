package org.probato.test.script;

import org.probato.api.Dataset;
import org.probato.api.Procedure;
import org.probato.api.Script;
import org.probato.model.type.Complexity;
import org.probato.model.type.Flow;
import org.probato.model.type.Relevance;

@Dataset("data/csv/file.csv")
@Script(
	code = "UC10TC01", 
	name = "Test case 10", 
	description = "This a simple test", 
	flow = Flow.MAIN, 
	complexity = Complexity.AVERAGE, 
	relevance = Relevance.AVERAGE)
public class UC10TC01_ScriptDataset {
	
	@Procedure
	void procedure() {}
	
}