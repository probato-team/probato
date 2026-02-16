package org.probato.test.script;

import org.probato.api.Dataset;
import org.probato.api.Page;
import org.probato.api.Postcondition;
import org.probato.api.Precondition;
import org.probato.api.Procedure;
import org.probato.api.Script;
import org.probato.test.datamodel.LoginData;
import org.probato.test.page.PrincipalPage;
import org.probato.test.procedure.PostconditionRun;
import org.probato.test.procedure.PreconditionRun;
import org.probato.test.procedure.ProcedureRun;
import org.probato.type.Complexity;
import org.probato.type.Flow;
import org.probato.type.Relevance;

@Dataset("data/csv/UC01TC01.csv")
@Script(
	code = "UC01TC01",
	name = "Test case 01",
	description = "This a simple test",
	flow = Flow.MAIN,
	complexity = Complexity.MEDIUM,
	relevance = Relevance.MEDIUM)
public class UC01TC01_Script {

	@Page
	private PrincipalPage page;

	@Precondition
	private PreconditionRun preconditionRun;

	@Precondition
	void precondition() {
		// Precondition implements
	}

	@Procedure
	private ProcedureRun run;

	@Procedure
	void procedure(LoginData data) {
		// Procedure implements
	}

	@Postcondition
	private PostconditionRun postconditionRun;

	@Postcondition
	void postcondition() {
		// Postcondition implements
	}

}