package org.probato.engine;

public abstract class ExecutionEngine {

	public void execute(ExecutionContext context) {
		init(context);
		run(context);
		finish();
	}

	public static ExecutionEngine get() {
		return new BrowserExecutionEngine();
	}

	protected abstract void init(ExecutionContext context);

	protected abstract void run(ExecutionContext context);

	protected abstract void finish();

}