package org.probato.test.suite;

import org.probato.api.Suite;
import org.probato.api.TestCase;
import org.probato.test.script.UC15TC01_ScriptWithInvalidProcedureMethod;

@Suite(code = "UC15", name = "Suite 15", description = "This a simple test")
public class UC15_SuiteWithInvalidProcedureMethod {

	@TestCase
	private UC15TC01_ScriptWithInvalidProcedureMethod uc15tc01;

}