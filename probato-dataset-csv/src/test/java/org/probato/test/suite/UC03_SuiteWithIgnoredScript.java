package org.probato.test.suite;

import org.probato.api.Ignore;
import org.probato.api.Suite;
import org.probato.api.TestCase;
import org.probato.test.script.UC02TC01_ScriptIgnored;

@Suite(code = "UC03", name = "Suite 03", description = "Suite 03")
public class UC03_SuiteWithIgnoredScript {

	@Ignore
	@TestCase
	private UC02TC01_ScriptIgnored uc03tc01;

}