package org.probato.test.suite;

import org.probato.api.SQL;
import org.probato.api.Suite;

@SQL(
	datasource = "without-url", 
	scriptPath = {})
@Suite(
	code = "UC17", 
	name = "Suite 17", 
	description = "This a simple test")
public class UC17_SuiteWithoutUrl {

}