package org.probato.test.script;

import org.probato.api.Script;
import org.probato.api.TestCase;
import org.probato.entity.type.Complexity;
import org.probato.entity.type.Flow;
import org.probato.entity.type.Relevance;

@Script(
	code = "UC01TC06", 
	name = UC06TC01_ScriptNameMaxLength.NAME, 
	description = "This a simple test", 
	flow = Flow.ALTERNATIVE, 
	complexity = Complexity.AVERAGE, 
	relevance = Relevance.AVERAGE)
public class UC06TC01_ScriptNameMaxLength {

	public static final String NAME = "This a simples test name many many many many many many many many many many many many many many many many many many many many many many many many many many many many many many many many many many many many many many many many many many many many many long";

	@TestCase
	private UC06TC01_ScriptNameMaxLength uc06tc01;

}