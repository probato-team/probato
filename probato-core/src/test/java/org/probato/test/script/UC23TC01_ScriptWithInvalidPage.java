package org.probato.test.script;

import org.probato.api.Page;
import org.probato.api.Script;
import org.probato.entity.type.Complexity;
import org.probato.entity.type.Flow;
import org.probato.entity.type.Relevance;
import org.probato.test.page.PageInvalidParam;

@Script(
	code = "UC23TC01", 
	name = "Test case 01", 
	description = "This a simple test", 
	flow = Flow.ALTERNATIVE, 
	complexity = Complexity.AVERAGE, 
	relevance = Relevance.AVERAGE)
public class UC23TC01_ScriptWithInvalidPage {
	
	@Page
	private PageInvalidParam page;
	
}