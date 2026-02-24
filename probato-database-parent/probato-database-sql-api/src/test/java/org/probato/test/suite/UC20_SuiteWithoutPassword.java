package org.probato.test.suite;

import org.probato.api.SQL;
import org.probato.api.Suite;

@SQL(
	datasource = "without-password", 
	scriptPath = {})
@Suite(
	code = "UC20", 
	name = "Suite 20", 
	description = "This a simple test")
public class UC20_SuiteWithoutPassword {

}