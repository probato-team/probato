package org.probato.test.suite;

import org.probato.api.SQL;
import org.probato.api.Suite;
import org.probato.api.TestCase;
import org.probato.test.script.UC23TC01_ScriptWithInvalidPage;

@SQL(datasource = "probato", scriptPath = { "path/to/file.sql", "path/to/file2.sql" })
@Suite(code = "UC23", name = "Suite 23", description = "This a simple test")
public class UC23_SuiteWithProcedurePage {
	
	@TestCase
	private UC23TC01_ScriptWithInvalidPage uc23tc01; 

}