package org.probato.api;

import static org.probato.junit.node.TestNodeService.createBrowserTestNode;
import static org.probato.junit.node.TestNodeService.createDatasetTestNode;
import static org.probato.junit.node.TestNodeService.createScriptTestNode;

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
import org.probato.junit.ProbatoForJUnit5;
import org.probato.junit.SuiteFactoryDisplayNameGenerator;
import org.probato.loader.AnnotationLoader;

@DisplayNameGeneration(SuiteFactoryDisplayNameGenerator.class)
@ExtendWith(ProbatoForJUnit5.class)
public abstract class TestSuite {

	@TestFactory
	@DisplayName("Test cases")
	Stream<DynamicNode> buildTestCase() {
		return Probato.getTestsCase(getClass())
				.sorted((scriptClazzA, scriptClazzB) -> {
					var scriptA = AnnotationLoader.getScript(scriptClazzA).get();
					var scriptB = AnnotationLoader.getScript(scriptClazzB).get();
					return scriptA.code().compareTo(scriptB.code());
				})
				.map(this::buildScriptTestNode);
	}

	DynamicNode buildScriptTestNode(Class<?> scriptClazz) {
		return Probato.loadScript(scriptClazz)
			.map(item -> createScriptTestNode(item, getClass(), buildDatasetTestNode(scriptClazz)))
			.orElse(createScriptTestNode(scriptClazz, buildDatasetTestNode(scriptClazz)));
	}

	List<DynamicNode> buildDatasetTestNode(Class<?> scriptClazz) {
		return Probato.loadDataset(scriptClazz)
			.map(dataset -> buildDatasetTestNode(scriptClazz, dataset))
			.orElse(buildTestNode(scriptClazz, 0));
	}

	List<DynamicNode> buildDatasetTestNode(Class<?> scriptClazz, Dataset dataset) {

		var list = new ArrayList<DynamicNode>();
		IntStream.range(0, Probato.getCsvCounterLines(dataset))
			.forEach(numberLine -> list
				.add(createDatasetTestNode(++numberLine, buildTestNode(scriptClazz, numberLine))));

		if (list.isEmpty()) {
			list.addAll(buildTestNode(scriptClazz, 0));
		}

		return list;
	}

	List<DynamicNode> buildTestNode(Class<?> scriptClazz, Integer datasetLine) {

		var list = new ArrayList<DynamicNode>();
		Probato.loadBrowsers(getClass())
				.map(browser -> createBrowserTestNode(browser, getClass(), scriptClazz, datasetLine))
				.forEach(list::add);

		return list;
	}

}