package org.probato.test.suite;

import org.probato.api.Ignore;
import org.probato.api.SQL;
import org.probato.api.Suite;
import org.probato.api.TestCase;
import org.probato.test.script.UC02TC01_ScriptIgnored;

@Ignore
@SQL(datasource = "probato", scriptPath = { "path/to/file.sql", "path/to/file2.sql" })
@Suite(code = "UC02", name = "Suite 02", description = "Suite ignored")
public class UC02_SuiteIgnored {

	@Ignore
	@TestCase
	private UC02TC01_ScriptIgnored uc09tc01;

}