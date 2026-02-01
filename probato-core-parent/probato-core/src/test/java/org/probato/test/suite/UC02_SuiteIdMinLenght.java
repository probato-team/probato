package org.probato.test.suite;

import org.probato.api.Suite;
import org.probato.api.TestCase;
import org.probato.test.script.UC02TC01_ScriptIdMinLength;

@Suite(code = "", name = "Suite 02", description = "This a simple test")
public class UC02_SuiteIdMinLenght {

	@TestCase
	private UC02TC01_ScriptIdMinLength uc02tc02;

}