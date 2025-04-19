package org.probato.test.suite;

import org.probato.api.SQL;
import org.probato.api.Suite;

@SQL(
	datasource = "without-username",
	scriptPath = {})
@Suite(
	code = "UC15",
	name = "Suite 15",
	description = "This a simple test")
public class UC15_SuiteWithoutUsername {

}