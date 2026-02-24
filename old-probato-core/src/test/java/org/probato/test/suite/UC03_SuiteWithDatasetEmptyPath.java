package org.probato.test.suite;

import org.probato.api.Suite;
import org.probato.api.TestCase;
import org.probato.test.script.UC03TC01_ScriptDatasetEmptyPath;

@Suite(
	code = "UC03", 
	name = "Suite 03", 
	description = "This a simple test")
public class UC03_SuiteWithDatasetEmptyPath {

	@TestCase
	private UC03TC01_ScriptDatasetEmptyPath uc03tc01;

}