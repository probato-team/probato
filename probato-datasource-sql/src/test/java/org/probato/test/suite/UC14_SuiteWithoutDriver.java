package org.probato.test.suite;

import org.probato.api.SQL;
import org.probato.api.Suite;

@SQL(
	datasource = "without-driver",
	scriptPath = {})
@Suite(
	code = "UC14",
	name = "Suite 14",
	description = "This a simple test")
public class UC14_SuiteWithoutDriver {

}