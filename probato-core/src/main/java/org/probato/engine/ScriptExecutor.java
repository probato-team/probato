package org.probato.engine;

import org.probato.model.Browser;

public interface ScriptExecutor {

	public void run();

	public static ScriptExecutor getInstance(Class<?> suiteClazz, Class<?> scriptClazz, Browser browser, Integer datasetLine) {
		return new BrowserScriptExecutionService(suiteClazz, scriptClazz, browser, datasetLine);
	}
	
	public static ScriptExecutor getInstance(Class<?> suiteClazz, Class<?> scriptClazz, Integer datasetLine) {
		return new DesktopScriptExecutionService(suiteClazz, scriptClazz, datasetLine);
	}
	
}