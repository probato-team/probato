package org.probato.test.suite;

import org.probato.api.Suite;
import org.probato.api.TestCase;
import org.probato.test.script.UC07TC01_ScriptWithInvalidDatamodel;

@Suite(
	code = "UC07",
	name = "Suite 07",
	description = "Suite 07")
public class UC07_SuiteWithInvalidDatamodel {

	@TestCase
	private UC07TC01_ScriptWithInvalidDatamodel uc07tc01;

}