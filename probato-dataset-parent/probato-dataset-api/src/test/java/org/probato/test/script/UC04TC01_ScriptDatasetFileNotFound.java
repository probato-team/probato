package org.probato.test.script;

import org.probato.api.Dataset;
import org.probato.api.Script;
import org.probato.type.Complexity;
import org.probato.type.Flow;
import org.probato.type.Relevance;

@Dataset("path/to/file-not-found.csv")
@Script(
	code = "UC04TC01",
	name = "Test case 01",
	description = "This a simple test",
	flow = Flow.MAIN,
	complexity = Complexity.MEDIUM,
	relevance = Relevance.MEDIUM)
public class UC04TC01_ScriptDatasetFileNotFound {}