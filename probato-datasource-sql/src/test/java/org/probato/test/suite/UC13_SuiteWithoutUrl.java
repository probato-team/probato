package org.probato.test.suite;

import org.probato.api.SQL;
import org.probato.api.Suite;

@SQL(
	datasource = "without-url",
	scriptPath = {})
@Suite(
	code = "UC13",
	name = "Suite 13",
	description = "This a simple test")
public class UC13_SuiteWithoutUrl {

}