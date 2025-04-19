package org.probato.test.script;

import org.probato.api.Procedure;
import org.probato.api.Script;
import org.probato.entity.type.Complexity;
import org.probato.entity.type.Flow;
import org.probato.entity.type.Relevance;
import org.probato.test.datamodel.DataInvalidConstructor;
import org.probato.test.procedure.ProcedureRun;

@Script(
	code = "UC01TC02",
	name = "Test case 02",
	description = "Test case 02",
	flow = Flow.MAIN,
	complexity = Complexity.AVERAGE,
	relevance = Relevance.AVERAGE)
public class UC01TC02_ScriptWithoutDataset {

	@Procedure
	private ProcedureRun run;

	@Procedure
	void procedure(DataInvalidConstructor data) {
		// Procedure implements
	}

}