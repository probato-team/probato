package org.probato.junit.node;

import org.junit.jupiter.api.function.Executable;
import org.probato.engine.ExecutionContext;
import org.probato.engine.ExecutionEngine;
import org.probato.model.Browser;

public class TestNodeExecutable implements Executable {

	private final ExecutionEngine engine;
	private final ExecutionContext context;

	public TestNodeExecutable(Browser browser, Class<?> suiteClazz, Class<?> scriptClazz, Integer datasetLine) {
		engine = ExecutionEngine.get();
		context = new ExecutionContext(browser, suiteClazz, scriptClazz, datasetLine);
	}

	@Override
	public void execute() throws Throwable {
		engine.execute(context);
	}

}