package org.probato.test.procedure;

import org.probato.api.Ignore;
import org.probato.api.Run;

@Ignore
public class ProcedureIgnoredRun {

	@Ignore
	@Run
	public void run() {}

}