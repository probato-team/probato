package org.probato.test.suite;

import org.probato.api.SQL;
import org.probato.api.Suite;
import org.probato.api.TestCase;
import org.probato.test.script.UC01TC01_ScriptWithSQL;

@SQL(
	datasource = "probato",
	scriptPath = { "data/sql/file.sql", "data/sql/file2.sql" })
@Suite(
	code = "UC01",
	name = "Suite 01",
	description = "Suite 01")
public class UC01_SuiteWithSQL {

	@TestCase
	private UC01TC01_ScriptWithSQL tc01;

}