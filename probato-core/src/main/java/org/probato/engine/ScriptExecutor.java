package org.probato.engine;

import org.probato.engine.browser.BrowserScriptExecutionService;
import org.probato.engine.desktop.DesktopScriptExecutionService;
import org.probato.entity.model.Browser;

public abstract class ScriptExecutor {

	public void run() {
		init();
		execute();
		finish();
	}

	protected abstract void init();

	protected abstract void execute();

	protected abstract void finish();

	public static ScriptExecutor getInstance(Class<?> suiteClazz, Class<?> scriptClazz, Browser browser, Integer datasetLine) {
		return new BrowserScriptExecutionService(suiteClazz, scriptClazz, browser, datasetLine);
	}
	
	public static ScriptExecutor getInstance(Class<?> suiteClazz, Class<?> scriptClazz, Integer datasetLine) {
		return new DesktopScriptExecutionService(suiteClazz, scriptClazz, datasetLine);
	}
	
}