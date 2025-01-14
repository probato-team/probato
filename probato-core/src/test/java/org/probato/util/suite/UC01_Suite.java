package org.probato.util.suite;

import org.probato.api.Suite;
import org.probato.api.TestCase;
import org.probato.util.script.UC01TC01_Script;

@Suite(
	code = "UC01", 
	name = "Suite 01", 
	description = "This a simple test")
public class UC01_Suite {

	@TestCase
	private UC01TC01_Script uc01tc01;

}