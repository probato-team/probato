package org.probato.test.suite;

import org.probato.api.Suite;
import org.probato.api.TestCase;
import org.probato.test.script.UC04TC01_ScriptIdMaxLength;

@Suite(code = "ÚÇ_04", name = "Suite 04", description = "This a simple test")
public class UC04_SuiteIdSpecialCharacter {

	@TestCase
	private UC04TC01_ScriptIdMaxLength uc04tc01;

}