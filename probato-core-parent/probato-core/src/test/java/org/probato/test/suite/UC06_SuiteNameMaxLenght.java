package org.probato.test.suite;

import org.probato.api.Suite;
import org.probato.api.TestCase;
import org.probato.test.script.UC06TC01_ScriptNameMaxLength;

@Suite(code = "UC06", name = "Suite name many many many many many many many many many many 01", description = "This a simple test")
public class UC06_SuiteNameMaxLenght {

	@TestCase
	private UC06TC01_ScriptNameMaxLength uc06tc01;

}