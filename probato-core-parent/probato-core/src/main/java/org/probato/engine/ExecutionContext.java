package org.probato.engine;

import org.probato.model.Browser;

public class ExecutionContext {

	private final Browser browser;
	private final Class<?> suiteClass;
	private final Class<?> scriptClass;
	private final int datasetLine;

	public ExecutionContext(
			Browser browser,
			Class<?> suiteClass,
			Class<?> scriptClass,
			int datasetLine) {

		this.browser = browser;
		this.suiteClass = suiteClass;
		this.scriptClass = scriptClass;
		this.datasetLine = datasetLine;
	}

	public Class<?> getSuiteClass() {
		return suiteClass;
	}

	public Class<?> getScriptClass() {
		return scriptClass;
	}

	public Browser getBrowser() {
		return browser;
	}

	public int getDatasetLine() {
		return datasetLine;
	}

}
