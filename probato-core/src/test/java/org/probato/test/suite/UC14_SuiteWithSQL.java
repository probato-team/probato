package org.probato.test.suite;

import org.probato.api.SQL;
import org.probato.api.Suite;
import org.probato.api.TestCase;
import org.probato.test.script.UC014TC01_ScriptWithSQL;

@SQL(datasource = "probato", scriptPath = { "path/to/file.sql", "path/to/file2.sql" })
@Suite(code = "UC14", name = "Suite 14", description = "This a simple test")
public class UC14_SuiteWithSQL {

	@TestCase
	private UC014TC01_ScriptWithSQL tc01;

}