package org.probato.engine;

public abstract class ExecutionEngine {

	public void execute(ExecutionContext context) {
		start(context);
		run(context);
		finish(context);
	}

	public static ExecutionEngine get() {
		return new BrowserExecutionEngine();
	}

	protected abstract void start(ExecutionContext context);

	protected abstract void run(ExecutionContext context);

	protected abstract void finish(ExecutionContext context);

}