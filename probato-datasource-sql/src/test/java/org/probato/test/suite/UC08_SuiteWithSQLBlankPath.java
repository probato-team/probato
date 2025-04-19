package org.probato.test.suite;

import org.probato.api.SQL;
import org.probato.api.Suite;

@SQL(
	datasource = "probato",
	scriptPath = { "", "" })
@Suite(
	code = "UC08",
	name = "Suite 08",
	description = "This a simple test")
public class UC08_SuiteWithSQLBlankPath {

}