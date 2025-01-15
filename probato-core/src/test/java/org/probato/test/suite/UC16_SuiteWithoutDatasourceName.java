package org.probato.test.suite;

import org.probato.api.SQL;
import org.probato.api.Suite;

@SQL(
	datasource = "", 
	scriptPath = {})
@Suite(
	code = "UC16", 
	name = "Suite 16", 
	description = "This a simple test")
public class UC16_SuiteWithoutDatasourceName {

}