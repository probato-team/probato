package org.probato.test.procedure;

import org.probato.api.Page;
import org.probato.api.Run;

public class ProcedureInvalidParam {
	
	@Page
	private ProcedureWithInvalidPage page;

	@Run
	public void run(String param) {}

}