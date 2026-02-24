package org.probato.test.suite;

import org.probato.api.SQL;
import org.probato.api.Suite;

@SQL(
	datasource = "probato", 
	scriptPath = { "", "" })
@Suite(
	code = "UC25", 
	name = "Suite 25", 
	description = "This a simple test")
public class UC25_SuiteWithSQLBlankPath {

}