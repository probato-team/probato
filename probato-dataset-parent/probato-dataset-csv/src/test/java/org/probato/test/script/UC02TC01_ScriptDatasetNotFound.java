package org.probato.test.script;

import org.probato.api.Dataset;
import org.probato.api.Procedure;
import org.probato.api.Script;
import org.probato.type.Complexity;
import org.probato.type.Flow;
import org.probato.type.Relevance;

@Dataset("path/to/file-not-found.csv")
@Script(
	code = "UC02TC01",
	name = "Test case 02",
	description = "This a simple test",
	flow = Flow.MAIN,
	complexity = Complexity.MEDIUM,
	relevance = Relevance.MEDIUM)
public class UC02TC01_ScriptDatasetNotFound {

	@Procedure
	void procedure() {}

}