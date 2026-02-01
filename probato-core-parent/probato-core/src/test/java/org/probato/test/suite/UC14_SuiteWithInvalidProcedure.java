package org.probato.test.suite;

import org.probato.api.Suite;
import org.probato.api.TestCase;
import org.probato.test.script.UC14TC01_ScriptWithInvalidProcedure;

@Suite(code = "UC14", name = "Suite 14", description = "This a simple test")
public class UC14_SuiteWithInvalidProcedure {

	@TestCase
	private UC14TC01_ScriptWithInvalidProcedure uc14tc01;

}