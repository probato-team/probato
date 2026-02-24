package org.probato.test.suite;

import org.probato.api.Suite;
import org.probato.api.TestCase;
import org.probato.test.script.UC08TC01_ScriptEmptyProcedure;

@Suite(code = "UC11", name = "Suite 11", description = "This a simple test")
public class UC11_SuiteScriptEmptyTestCase {

	@TestCase
	private UC08TC01_ScriptEmptyProcedure uc08tc01;

}