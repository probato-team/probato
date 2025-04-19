package org.probato.node;

import org.junit.jupiter.api.function.Executable;
import org.probato.engine.ScriptExecutor;
import org.probato.entity.model.Browser;

public class TestNodeExecutable implements Executable {

	private final ScriptExecutor scriptExecutor;

	public TestNodeExecutable(Class<?> suiteClazz, Class<?> scriptClazz, Browser browser, Integer datasetLine) {
		this.scriptExecutor = ScriptExecutor.getInstance(suiteClazz, scriptClazz, browser, datasetLine);
	}

	public TestNodeExecutable(Class<?> suiteClazz, Class<?> scriptClazz, Integer datasetLine) {
		this.scriptExecutor = ScriptExecutor.getInstance(suiteClazz, scriptClazz, datasetLine);
	}

	@Override
	public void execute() throws Throwable {
		scriptExecutor.run();
	}

}