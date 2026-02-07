package org.probato.engine.procedure;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.probato.test.script.UC01TC01_Script;

@DisplayName("UT -> ProcedureBuilder")
class ProcedureBuilderTest {

	@Test
	@DisplayName("Should build procedures unit by script class successfuly")
	void shouldLoadProceduresSuccessfully() {

		var script = UC01TC01_Script.class;

		var discovery = ProcedureDiscovery.get();
		var builder = ProcedureBuilder.get();

		var procedures = discovery.discover(script);
		var units = builder.build(procedures, script);

		assertEquals(6, units.size());
	}

}