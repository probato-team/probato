package org.probato.test.suite;

import org.probato.api.Ignore;
import org.probato.api.Suite;
import org.probato.api.TestCase;
import org.probato.test.script.UC09TC01_ScriptIgnored;

@Suite(code = "UC12", name = "Suite 12", description = "This a simple test")
public class UC12_SuiteWithIgnoredScript {

	@Ignore
	@TestCase
	private UC09TC01_ScriptIgnored uc09tc01;

}