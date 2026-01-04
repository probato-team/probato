package org.probato.test.suite;

import org.probato.api.Suite;
import org.probato.api.TestCase;
import org.probato.test.script.UC02TC01_ScriptDatasetNoPaths;

@Suite(
	code = "UC02", 
	name = "Suite 02", 
	description = "This a simple test")
public class UC02_SuiteWithDatasetNoPaths {

	@TestCase
	private UC02TC01_ScriptDatasetNoPaths uc02tc01;

}