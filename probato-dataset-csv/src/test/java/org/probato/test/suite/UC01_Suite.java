package org.probato.test.suite;

import org.probato.api.Suite;
import org.probato.api.TestCase;
import org.probato.test.script.UC01TC01_Script;
import org.probato.test.script.UC01TC02_ScriptWithoutDataset;

@Suite(
	code = "UC01",
	name = "Suite 01",
	description = "Suite 01")
public class UC01_Suite {

	@TestCase
	private UC01TC01_Script uc01tc01;

	@TestCase
	private UC01TC02_ScriptWithoutDataset uc01tc02;

}