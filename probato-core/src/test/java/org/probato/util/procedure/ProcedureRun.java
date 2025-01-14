package org.probato.util.procedure;

import org.probato.api.Page;
import org.probato.api.Run;
import org.probato.util.page.PrincipalPage;

public class ProcedureRun {

	@Page
	private PrincipalPage page;

	@Run
	public void run() {
		// Run implements
	}

}