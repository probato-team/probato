package org.probato.test.script;

import org.probato.api.Dataset;
import org.probato.api.Script;
import org.probato.entity.type.Complexity;
import org.probato.entity.type.Flow;
import org.probato.entity.type.Relevance;

@Dataset("path/to/file-not-found.csv")
@Script(
	code = "UC04TC01", 
	name = "Test case 01", 
	description = "This a simple test", 
	flow = Flow.MAIN, 
	complexity = Complexity.AVERAGE, 
	relevance = Relevance.AVERAGE)
public class UC04TC01_ScriptDatasetFileNotFound {}