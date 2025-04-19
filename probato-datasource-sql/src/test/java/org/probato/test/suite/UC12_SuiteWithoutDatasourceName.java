package org.probato.test.suite;

import org.probato.api.SQL;
import org.probato.api.Suite;

@SQL(
	datasource = "",
	scriptPath = {})
@Suite(
	code = "UC12",
	name = "Suite 12",
	description = "This a simple test")
public class UC12_SuiteWithoutDatasourceName {

}