package org.probato.test.suite;

import org.probato.api.NoSQL;
import org.probato.api.Suite;

@NoSQL(
	datasource = "probato-mongo",
	scriptPath = { "", "" })
@Suite(
	code = "UC25",
	name = "Suite 25",
	description = "This a simple test")
public class UC25_SuiteWithMongoNoSQLBlankPath {

}