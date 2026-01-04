package org.probato.test.suite;

import org.probato.api.Suite;
import org.probato.api.TestCase;
import org.probato.test.script.UC10TC01_ScriptWithInvalidProcedure;

@Suite(code = "UC10", name = "Suite 10", description = "This a simple test")
public class UC10_SuiteWithInvalidProcedure {

	@TestCase
	private UC10TC01_ScriptWithInvalidProcedure uc09tc01;

}