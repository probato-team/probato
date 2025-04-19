package org.probato.node;

import java.util.List;

import org.junit.jupiter.api.DynamicNode;
import org.probato.api.Script;
import org.probato.entity.model.Browser;

public interface TestNodeService {

	public static DynamicNode createScriptTestNode(Script script, List<DynamicNode> subList) {
		return new ScriptTestNode(script, subList.stream()).create();
	}

	public static DynamicNode createScriptTestNode(Class<?> scriptClazz, List<DynamicNode> subList) {
		return new ScriptTestNode(scriptClazz, subList.stream()).create();
	}

	public static DynamicNode createDatasetTestNode(int numberLine, List<DynamicNode> subList) {
		return new DatasetTestNode(numberLine, subList.stream()).create();
	}

	public static DynamicNode createBrowserTestNode(Class<?> suiteClazz, Class<?> scriptClazz, Browser browser, Integer datasetLine) {
		return new BrowserTestNode(suiteClazz, scriptClazz, browser, datasetLine).create();
	}

	public static DynamicNode createDesktopTestNode(Class<?> suiteClazz, Class<?> scriptClazz, Integer datasetLine) {
		return new DesktopTestNode(suiteClazz, scriptClazz, datasetLine).create();
	}

}