package org.probato.test.suite;

import org.probato.api.SQL;
import org.probato.api.Suite;

@SQL(
	datasource = "probato", 
	scriptPath = { "data/sql/invalid-file.sql" })
@Suite(
	code = "UC27", 
	name = "Suite 27", 
	description = "This a simple test")
public class UC27_SuiteWithInvalidSQL {

}