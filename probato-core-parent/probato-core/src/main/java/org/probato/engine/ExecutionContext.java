package org.probato.engine;

public class ExecutionContext {

	private final Class<?> suiteClass;
	private final Class<?> scriptClass;
	private final Object browser;
	private final int datasetLine;

	public ExecutionContext(
			Class<?> suiteClass,
			Class<?> scriptClass,
			Object browser,
			int datasetLine) {

		this.suiteClass = suiteClass;
		this.scriptClass = scriptClass;
		this.browser = browser;
		this.datasetLine = datasetLine;
	}

	public Class<?> getSuiteClass() {
		return suiteClass;
	}

	public Class<?> getScriptClass() {
		return scriptClass;
	}

	public Object getBrowser() {
		return browser;
	}

	public int getDatasetLine() {
		return datasetLine;
	}

}
