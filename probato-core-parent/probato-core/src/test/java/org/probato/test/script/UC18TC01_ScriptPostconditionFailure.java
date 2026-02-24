package org.probato.test.script;

import org.probato.api.Postcondition;
import org.probato.api.Precondition;
import org.probato.api.Procedure;
import org.probato.api.Script;
import org.probato.test.procedure.PreconditionRun;
import org.probato.test.procedure.ProcedureFailureRun;
import org.probato.test.procedure.ProcedureRun;
import org.probato.type.Complexity;
import org.probato.type.Flow;
import org.probato.type.Relevance;

@Script(
	code = "UC18TC01",
	name = "Test case 18",
	description = "This a simple test",
	flow = Flow.MAIN,
	complexity = Complexity.MEDIUM,
	relevance = Relevance.MEDIUM)
public class UC18TC01_ScriptPostconditionFailure {

	@Precondition
	private PreconditionRun preconditionRun;

	@Precondition
	void precondition() {
		// Precondition implements
	}

	@Procedure
	private ProcedureRun run;

	@Procedure
	void procedure() {
		// Procedure implements
	}

	@Postcondition
	private ProcedureFailureRun postconditionRun;

	@Postcondition
	void postcondition() {
		// Procedure implements
	}

}