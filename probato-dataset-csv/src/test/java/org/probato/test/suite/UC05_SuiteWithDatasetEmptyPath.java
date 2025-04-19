package org.probato.test.suite;

import org.probato.api.Suite;
import org.probato.api.TestCase;
import org.probato.test.script.UC05TC01_ScriptDatasetEmptyPath;

@Suite(
	code = "UC05",
	name = "Suite 05",
	description = "Suite 05")
public class UC05_SuiteWithDatasetEmptyPath {

	@TestCase
	private UC05TC01_ScriptDatasetEmptyPath uc05tc01;

}