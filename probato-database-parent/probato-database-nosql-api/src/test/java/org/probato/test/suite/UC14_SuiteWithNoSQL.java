package org.probato.test.suite;

import org.probato.api.NoSQL;
import org.probato.api.Suite;
import org.probato.api.TestCase;
import org.probato.test.script.UC014TC01_ScriptWithSQL;

@NoSQL(
	datasource = "probato",
	scriptPath = { "data/nosql/file.json" })
@Suite(
	code = "UC14",
	name = "Suite 14",
	description = "This a simple test")
public class UC14_SuiteWithNoSQL {

	@TestCase
	private UC014TC01_ScriptWithSQL tc01;

}