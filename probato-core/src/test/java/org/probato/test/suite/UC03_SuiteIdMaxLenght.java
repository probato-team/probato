package org.probato.test.suite;

import org.probato.api.Suite;
import org.probato.api.TestCase;
import org.probato.test.script.UC03TC01_ScriptIdMaxLength;

@Suite(code = "UC0000000000000000001", name = "Suite 03", description = "This a simple test")
public class UC03_SuiteIdMaxLenght {

	@TestCase
	private UC03TC01_ScriptIdMaxLength uc03tc01;

}