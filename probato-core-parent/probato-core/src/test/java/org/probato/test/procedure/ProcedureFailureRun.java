package org.probato.test.procedure;

import org.probato.api.Run;

public class ProcedureFailureRun {

	@Run
	public void run() {
		System.out.println(1/0);
	}

}