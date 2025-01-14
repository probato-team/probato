package org.probato.test.suite;

import org.probato.api.SQL;
import org.probato.api.SQLs;
import org.probato.api.Suite;
import org.probato.api.TestCase;
import org.probato.test.script.UC01TC01_Script;

@SQLs(
	@SQL(
		datasource = "probato", 
		scriptPath = { "path/to/file.sql", "path/to/file2.sql" }))
@Suite(
	code = "UC10", 
	name = "Suite 10", 
	description = "Suite with sql")
public class UC10_SuiteWithSql {

	@TestCase
	private UC01TC01_Script uc01tc01;

}