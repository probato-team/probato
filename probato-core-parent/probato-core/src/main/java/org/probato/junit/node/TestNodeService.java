package org.probato.junit.node;

import java.util.List;

import org.junit.jupiter.api.DynamicNode;
import org.probato.api.Script;
import org.probato.model.Browser;

public interface TestNodeService {

	public static DynamicNode createScriptTestNode(Script script, Class<?> scriptClazz, List<DynamicNode> nodes) {
		return new ScriptTestNode(script, scriptClazz, nodes.stream()).create();
	}

	public static DynamicNode createScriptTestNode(Class<?> scriptClazz, List<DynamicNode> nodes) {
		return new ScriptTestNode(scriptClazz, nodes.stream()).create();
	}

	public static DynamicNode createDatasetTestNode(int numberLine, List<DynamicNode> nodes) {
		return new DatasetTestNode(numberLine, nodes.stream()).create();
	}

	public static DynamicNode createBrowserTestNode(Browser browser, Class<?> suiteClazz, Class<?> scriptClazz, Integer datasetLine) {
		return new BrowserTestNode(browser, suiteClazz, scriptClazz, datasetLine).create();
	}

	public static DynamicNode createDesktopTestNode(Class<?> suiteClazz, Class<?> scriptClazz, Integer datasetLine) {
		return new DesktopTestNode(suiteClazz, scriptClazz, datasetLine).create();
	}

}