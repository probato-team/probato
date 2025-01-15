package org.probato.test.suite;

import org.probato.api.Suite;
import org.probato.api.TestCase;
import org.probato.test.script.UC04TC01_ScriptDatasetFileNotFound;

@Suite(
	code = "UC04", 
	name = "Suite 04", 
	description = "This a simple test")
public class UC04_SuiteWithDatasetFileNotFound {

	@TestCase
	private UC04TC01_ScriptDatasetFileNotFound uc02tc01;

}