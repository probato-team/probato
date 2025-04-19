package org.probato.test.script;

import org.probato.api.Script;
import org.probato.entity.type.Complexity;
import org.probato.entity.type.Flow;
import org.probato.entity.type.Relevance;

@Script(
	code = "UC01TC08", 
	name = "UC01TC08", 
	description = UC08TC01_ScriptEmptyProcedure.DESCRIPTION, 
	flow = Flow.ALTERNATIVE, 
	complexity = Complexity.AVERAGE, 
	relevance = Relevance.AVERAGE)
public class UC08TC01_ScriptEmptyProcedure {

	public static final String DESCRIPTION = "UC01TC07_ScriptDescriptionMaxLength";

}