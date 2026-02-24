package org.probato.test.script;

import org.probato.api.Page;
import org.probato.api.Procedure;
import org.probato.api.Script;
import org.probato.test.page.PageError;
import org.probato.type.Complexity;
import org.probato.type.Flow;
import org.probato.type.Relevance;

@Script(
	code = "UC24TC01",
	name = "Test case 24",
	description = "This a simple test",
	flow = Flow.MAIN,
	complexity = Complexity.MEDIUM,
	relevance = Relevance.MEDIUM)
public class UC24TC01_ScriptWithPreconditionPageError {

	@Page
	private PageError page;

	@Procedure
	void procedure() {
		page.actionError();
	}

}