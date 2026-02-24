package org.probato.test.procedure;

import org.probato.api.Run;

public class ProcedureErrorRun {

	@Run
	public void run() throws Exception {
		throw new Exception("This a simple error test");
	}

}