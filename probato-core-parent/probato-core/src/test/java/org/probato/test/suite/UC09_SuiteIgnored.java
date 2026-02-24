package org.probato.test.suite;

import org.probato.api.Ignore;
import org.probato.api.SQL;
import org.probato.api.Suite;
import org.probato.api.TestCase;
import org.probato.test.script.UC09TC01_ScriptIgnored;

@Ignore
@SQL(datasource = "probato", scriptPath = { "path/to/file.sql", "path/to/file2.sql" })
@Suite(code = "UC09", name = "Suite 09", description = "This a simple test")
public class UC09_SuiteIgnored {

	@Ignore
	@TestCase
	private UC09TC01_ScriptIgnored uc09tc01;

}