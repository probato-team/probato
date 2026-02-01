package org.probato.test.script;

import org.probato.api.Script;
import org.probato.type.Complexity;
import org.probato.type.Flow;
import org.probato.type.Relevance;

@Script(
	code = "UC01TC08",
	name = "UC01TC08",
	description = UC08TC01_ScriptEmptyProcedure.DESCRIPTION,
	flow = Flow.ALTERNATIVE,
	complexity = Complexity.MEDIUM,
	relevance = Relevance.MEDIUM)
public class UC08TC01_ScriptEmptyProcedure {

	public static final String DESCRIPTION = "UC01TC07_ScriptDescriptionMaxLength";

}