package org.probato.engine.procedure;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.probato.test.script.UC01TC01_Script;

@DisplayName("UT -> ProcedureDiscovery")
class ProcedureDiscoveryTest {

	@Test
	@DisplayName("Should load procedures by script class successfuly")
	void shouldLoadProceduresSuccessfully() {

		var discovery = ProcedureDiscovery.get();

		var procedures = discovery.discover(UC01TC01_Script.class);

		assertEquals(6, procedures.size());
	}

}