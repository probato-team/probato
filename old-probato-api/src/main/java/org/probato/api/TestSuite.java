package org.probato.api;

import static org.probato.node.TestNodeService.createBrowserTestNode;
import static org.probato.node.TestNodeService.createDatasetTestNode;
import static org.probato.node.TestNodeService.createScriptTestNode;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DynamicNode;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.extension.ExtendWith;
import org.probato.Probato;
import org.probato.junit.SuiteFactoryDisplayNameGenerator;
import org.probato.junit.TestanoForJUnit5;

@DisplayNameGeneration(SuiteFactoryDisplayNameGenerator.class)
@ExtendWith(TestanoForJUnit5.class)
public interface TestSuite {

	@TestFactory
	@DisplayName("Test cases")
	default Stream<DynamicNode> buildTestCase() {
		return Probato.getTestsCase(getClass())
				.map(this::buildScriptTestNode);
	}

	default DynamicNode buildScriptTestNode(Class<?> scriptClazz) {
		return Probato.loadScript(scriptClazz)
			.map(item -> createScriptTestNode(item, buildDatasetTestNode(scriptClazz)))
			.orElse(createScriptTestNode(scriptClazz, buildDatasetTestNode(scriptClazz)));
	}

	default List<DynamicNode> buildDatasetTestNode(Class<?> scriptClazz) {
		return Probato.loadDataset(scriptClazz)
			.map(dataset -> buildDatasetTestNode(scriptClazz, dataset))
			.orElse(buildTestNode(scriptClazz, 0));
	}

	default List<DynamicNode> buildDatasetTestNode(Class<?> scriptClazz, Dataset dataset) {

		var list = new ArrayList<DynamicNode>();
		IntStream.range(0, Probato.getCsvCounterLines(dataset))
			.forEach(numberLine -> list
				.add(createDatasetTestNode(++numberLine, buildTestNode(scriptClazz, numberLine))));

		if (list.isEmpty()) {
			list.addAll(buildTestNode(scriptClazz, 0));
		}

		return list;
	}

	default List<DynamicNode> buildTestNode(Class<?> scriptClazz, Integer datasetLine) {
		
		var list = new ArrayList<DynamicNode>();
		Probato.loadBrowsers(getClass())
				.map(browser -> createBrowserTestNode(getClass(), scriptClazz, browser, datasetLine))
				.forEach(list::add);

		return list;
	}

}