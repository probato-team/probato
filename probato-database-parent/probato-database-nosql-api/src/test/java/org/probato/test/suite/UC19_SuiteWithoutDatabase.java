package org.probato.test.suite;

import org.probato.api.NoSQL;
import org.probato.api.Suite;

@NoSQL(
	datasource = "without-database",
	scriptPath = {})
@Suite(
	code = "UC19",
	name = "Suite 19",
	description = "This a simple test")
public class UC19_SuiteWithoutDatabase {

}