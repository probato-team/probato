package org.probato.test.suite;

import org.probato.api.Suite;
import org.probato.api.TestCase;
import org.probato.test.script.UC08TC01_ScriptEmptyProcedure;

@Suite(code = "UC08", name = "Suite 08", description = "This a simple test")
public class UC08_SuiteScriptEmptyTestCase {

	@TestCase
	private UC08TC01_ScriptEmptyProcedure uc08tc01;

}