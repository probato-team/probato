package org.probato.test.suite;

import org.probato.api.Suite;
import org.probato.api.TestCase;
import org.probato.test.script.UC05TC01_ScriptNameMinLength;

@Suite(code = "UC05", name = "Su", description = "This a simple test")
public class UC05_SuiteNameMinLenght {

	@TestCase
	private UC05TC01_ScriptNameMinLength uc05tc01;

}