package org.probato.test.suite;

import org.probato.api.Suite;
import org.probato.api.TestCase;
import org.probato.test.script.UC13TC01_ScriptWithIgnoredProcedure;

@Suite(code = "UC13", name = "Suite 13", description = "This a simple test")
public class UC13_SuiteWithIgnoredProcedure {

	@TestCase
	private UC13TC01_ScriptWithIgnoredProcedure uc09tc01;

}