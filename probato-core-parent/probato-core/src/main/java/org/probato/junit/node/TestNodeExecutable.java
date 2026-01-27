package org.probato.junit.node;

import org.junit.jupiter.api.function.Executable;
import org.probato.engine.ExecutionContext;
import org.probato.engine.ExecutionEngine;
import org.probato.model.Browser;

public class TestNodeExecutable implements Executable {

	private final ExecutionEngine engine;
	private final ExecutionContext context;

	public TestNodeExecutable(Class<?> suiteClazz, Class<?> scriptClazz, Browser browser, Integer datasetLine) {
		engine = new ExecutionEngine();
		context = new ExecutionContext(suiteClazz, scriptClazz, browser, datasetLine);
	}

	@Override
	public void execute() throws Throwable {
		var record = engine.execute(context);
	}

}