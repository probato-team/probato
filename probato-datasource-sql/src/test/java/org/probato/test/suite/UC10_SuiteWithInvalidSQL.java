package org.probato.test.suite;

import org.probato.api.SQL;
import org.probato.api.Suite;

@SQL(
	datasource = "probato",
	scriptPath = { "data/sql/invalid-file.sql" })
@Suite(
	code = "UC10",
	name = "Suite 10",
	description = "This a simple test")
public class UC10_SuiteWithInvalidSQL {

}