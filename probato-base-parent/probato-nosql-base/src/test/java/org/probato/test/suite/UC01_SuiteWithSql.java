package org.probato.test.suite;

import org.probato.api.NoSQL;
import org.probato.api.NoSQLs;
import org.probato.api.Suite;
import org.probato.api.TestCase;
import org.probato.test.script.UC01TC01_Script;

@NoSQLs(
	@NoSQL(
		datasource = "probato",
		scriptPath = { "path/to/file.sql" })
)
@Suite(
	code = "UC01",
	name = "Suite 01",
	description = "Suite with sql")
public class UC01_SuiteWithSql {

	@TestCase
	private UC01TC01_Script uc01tc01;

}