package org.probato.test.suite;

import org.probato.api.Suite;
import org.probato.api.TestCase;
import org.probato.test.script.UC04TC01_ScriptDatasetNoPaths;

@Suite(
	code = "UC04",
	name = "Suite 04",
	description = "Suite 04")
public class UC04_SuiteWithDatasetNoPaths {

	@TestCase
	private UC04TC01_ScriptDatasetNoPaths uc04tc01;

}