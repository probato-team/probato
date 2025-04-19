package org.probato.test.suite;

import org.probato.api.SQL;
import org.probato.api.Suite;

@SQL(
	datasource = "probato",
	scriptPath = {})
@Suite(
	code = "UC07",
	name = "Suite 07",
	description = "This a simple test")
public class UC07_SuiteWithSQLEmptyPath {

}