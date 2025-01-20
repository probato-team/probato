package org.probato.test.suite;

import org.probato.api.Suite;
import org.probato.api.TestCase;
import org.probato.test.script.UC11TC01_ScriptWithInvalidProcedureMethod;

@Suite(code = "UC11", name = "Suite 11", description = "This a simple test")
public class UC11_SuiteWithInvalidProcedureMethod {

	@TestCase
	private UC11TC01_ScriptWithInvalidProcedureMethod uc09tc01;

}