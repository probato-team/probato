package org.probato.test.suite;

import org.probato.api.NoSQL;
import org.probato.api.Suite;

@NoSQL(
	datasource = "without-type",
	scriptPath = {})
@Suite(
	code = "UC18",
	name = "Suite 18",
	description = "This a simple test")
public class UC18_SuiteWithoutType {

}