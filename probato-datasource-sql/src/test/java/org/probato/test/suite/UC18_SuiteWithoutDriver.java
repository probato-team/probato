package org.probato.test.suite;

import org.probato.api.SQL;
import org.probato.api.Suite;

@SQL(
	datasource = "without-driver", 
	scriptPath = {})
@Suite(
	code = "UC18", 
	name = "Suite 18", 
	description = "This a simple test")
public class UC18_SuiteWithoutDriver {

}