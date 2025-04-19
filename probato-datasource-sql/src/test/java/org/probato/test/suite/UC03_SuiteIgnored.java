package org.probato.test.suite;

import org.probato.api.Ignore;
import org.probato.api.SQL;
import org.probato.api.Suite;
import org.probato.api.TestCase;
import org.probato.test.script.UC03TC01_ScriptIgnored;

@Ignore
@SQL(datasource = "probato", scriptPath = { "path/to/file.sql", "path/to/file2.sql" })
@Suite(code = "UC03", name = "Suite 03", description = "Suite 03")
public class UC03_SuiteIgnored {

	@Ignore
	@TestCase
	private UC03TC01_ScriptIgnored uc03tc01;

}