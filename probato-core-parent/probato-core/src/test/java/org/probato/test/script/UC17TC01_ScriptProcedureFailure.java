package org.probato.test.script;

import org.probato.api.Postcondition;
import org.probato.api.Precondition;
import org.probato.api.Procedure;
import org.probato.api.Script;
import org.probato.test.procedure.PostconditionRun;
import org.probato.test.procedure.PreconditionRun;
import org.probato.test.procedure.ProcedureFailureRun;
import org.probato.type.Complexity;
import org.probato.type.Flow;
import org.probato.type.Relevance;

@Script(
	code = "UC17TC01",
	name = "Test case 17",
	description = "This a simple test",
	flow = Flow.MAIN,
	complexity = Complexity.MEDIUM,
	relevance = Relevance.MEDIUM)
public class UC17TC01_ScriptProcedureFailure {

	@Precondition
	private PreconditionRun preconditionRun;

	@Precondition
	void precondition() {
		// Precondition implements
	}

	@Procedure
	private ProcedureFailureRun run;

	@Procedure
	void procedure() {
		// Procedure implements
	}

	@Postcondition
	private PostconditionRun postconditionRun;

	@Postcondition
	void postcondition() {
		// Postcondition implements
	}

}