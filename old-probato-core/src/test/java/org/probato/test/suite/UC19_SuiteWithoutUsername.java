package org.probato.test.suite;

import org.probato.api.SQL;
import org.probato.api.Suite;

@SQL(
	datasource = "without-username", 
	scriptPath = {})
@Suite(
	code = "UC19", 
	name = "Suite 19", 
	description = "This a simple test")
public class UC19_SuiteWithoutUsername {

}