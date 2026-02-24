package org.probato.test.suite;

import org.probato.api.SQL;
import org.probato.api.Suite;

@SQL(
	datasource = "probato", 
	scriptPath = {})
@Suite(
	code = "UC24", 
	name = "Suite 24", 
	description = "This a simple test")
public class UC24_SuiteWithSQLEmptyPath {

}