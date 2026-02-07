package org.probato.engine;

import java.util.List;

import org.probato.browser.BrowserSession;
import org.probato.browser.NativeBrowserSession;
import org.probato.engine.procedure.ExecutableUnit;
import org.probato.engine.procedure.ExecutionResult;
import org.probato.engine.procedure.ProcedureService;
import org.probato.service.BrowserService;

public class BrowserExecutionEngine extends ExecutionEngine {

	private final BrowserService browserService;
	private final ProcedureService procedureService;

	private ExecutionResult result;
	private BrowserSession browserSession;
	private List<ExecutableUnit> procedures;

	public BrowserExecutionEngine() {
		this.browserService = BrowserService.get();
		this.procedureService = ProcedureService.get();
	}

	@Override
	protected void init(ExecutionContext context) {
		this.browserSession = browserService.createSession(context.getBrowser());
		loadProcedures(context);
	}

	private void loadProcedures(ExecutionContext context) {
		this.procedures = procedureService.load(context.getScriptClass());
	}

	@Override
	protected void run(ExecutionContext context) {
		try {

			browserSession.run();

			var driver = ((NativeBrowserSession<?>) browserSession).driver();
			result = procedureService.execute(context, driver, procedures);

		} finally {
			browserSession.destroy();
		}
	}

	@Override
	protected void finish() {
		System.out.println("Finish: " + result.getStatus());
	}

}