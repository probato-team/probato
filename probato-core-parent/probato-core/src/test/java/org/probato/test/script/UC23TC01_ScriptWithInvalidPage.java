package org.probato.test.script;

import org.probato.api.Page;
import org.probato.api.Script;
import org.probato.test.page.PageInvalidParam;
import org.probato.type.Complexity;
import org.probato.type.Flow;
import org.probato.type.Relevance;

@Script(
	code = "UC23TC01",
	name = "Test case 01",
	description = "This a simple test",
	flow = Flow.ALTERNATIVE,
	complexity = Complexity.MEDIUM,
	relevance = Relevance.MEDIUM)
public class UC23TC01_ScriptWithInvalidPage {

	@Page
	private PageInvalidParam page;

}