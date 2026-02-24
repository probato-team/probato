package org.probato.engine.junit;

import java.util.List;

import org.junit.jupiter.api.DynamicNode;
import org.probato.engine.junit.node.BrowserTestNode;
import org.probato.engine.junit.node.DatasetTestNode;
import org.probato.engine.junit.node.ScriptTestNode;
import org.probato.model.Browser;

public interface TestNodeService {

	public static DynamicNode createScriptTestNode(Class<?> scriptClazz, List<DynamicNode> nodes) {
		return new ScriptTestNode(scriptClazz, nodes.stream()).create();
	}

	public static DynamicNode createDatasetTestNode(Class<?> scriptClazz, int numberLine, List<DynamicNode> nodes) {
		return new DatasetTestNode(scriptClazz, numberLine, nodes.stream()).create();
	}

	public static DynamicNode createBrowserTestNode(Browser browser, Class<?> suiteClazz, Class<?> scriptClazz, Integer datasetLine) {
		return new BrowserTestNode(browser, suiteClazz, scriptClazz, datasetLine).create();
	}

}