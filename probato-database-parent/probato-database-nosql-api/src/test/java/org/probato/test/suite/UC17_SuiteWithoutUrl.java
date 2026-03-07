package org.probato.test.suite;

import org.probato.api.NoSQL;
import org.probato.api.Suite;

@NoSQL(
	datasource = "without-url",
	scriptPath = {})
@Suite(
	code = "UC17",
	name = "Suite 17",
	description = "This a simple test")
public class UC17_SuiteWithoutUrl {

}