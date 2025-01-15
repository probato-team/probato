package org.probato.test.suite;

import org.probato.api.SQL;
import org.probato.api.Suite;
import org.probato.api.TestCase;
import org.probato.test.script.UC28TC01_ScriptWithDataset;

@SQL(datasource = "probato", scriptPath = { "path/to/file.sql", "path/to/file2.sql" })
@Suite(code = "UC28", name = "Suite 28", description = "This a simple test")
public class UC28_SuiteWithScriptDataset {

	@TestCase
	private UC28TC01_ScriptWithDataset tc01;

}