package org.probato.test.suite;

import org.probato.api.Suite;
import org.probato.api.TestCase;
import org.probato.test.script.UC06TC01_ScriptDatasetFileNotFound;

@Suite(
	code = "UC06",
	name = "Suite 06",
	description = "Suite 06")
public class UC06_SuiteWithDatasetFileNotFound {

	@TestCase
	private UC06TC01_ScriptDatasetFileNotFound uc06tc01;

}