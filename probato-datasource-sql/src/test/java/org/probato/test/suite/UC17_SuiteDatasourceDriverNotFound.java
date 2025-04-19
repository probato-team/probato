package org.probato.test.suite;

import org.probato.api.SQL;
import org.probato.api.Suite;

@SQL(
	datasource = "driver-not-found",
	scriptPath = { "data/sql/file.sql" })
@Suite(
	code = "UC17",
	name = "Suite 17",
	description = "This a simple test")
public class UC17_SuiteDatasourceDriverNotFound {

}