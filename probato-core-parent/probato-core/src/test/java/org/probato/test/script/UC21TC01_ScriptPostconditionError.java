package org.probato.test.script;

import org.probato.api.Postcondition;
import org.probato.api.Precondition;
import org.probato.api.Procedure;
import org.probato.api.Script;
import org.probato.test.procedure.PreconditionRun;
import org.probato.test.procedure.ProcedureErrorRun;
import org.probato.test.procedure.ProcedureRun;
import org.probato.type.Complexity;
import org.probato.type.Flow;
import org.probato.type.Relevance;

@Script(
	code = "UC21TC01",
	name = "Test case 21",
	description = "This a simple test",
	flow = Flow.MAIN,
	complexity = Complexity.MEDIUM,
	relevance = Relevance.MEDIUM)
public class UC21TC01_ScriptPostconditionError {

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
	private ProcedureErrorRun postconditionRun;

	@Postcondition
	void postcondition() {
		// Procedure implements
	}

}