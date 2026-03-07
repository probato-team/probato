package org.probato.test.suite;

import org.probato.api.Ignore;
import org.probato.api.NoSQL;
import org.probato.api.Suite;
import org.probato.api.TestCase;
import org.probato.test.script.UC09TC01_ScriptIgnored;

@Ignore
@NoSQL(datasource = "probato", scriptPath = { "path/to/file.sql" })
@Suite(code = "UC09", name = "Suite 09", description = "This a simple test")
public class UC09_SuiteIgnored {

	@Ignore
	@TestCase
	private UC09TC01_ScriptIgnored uc09tc01;

}