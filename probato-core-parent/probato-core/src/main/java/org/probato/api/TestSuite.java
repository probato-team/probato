package org.probato.api;

import static org.probato.engine.junit.TestNodeService.createBrowserTestNode;
import static org.probato.engine.junit.TestNodeService.createDatasetTestNode;
import static org.probato.engine.junit.TestNodeService.createScriptTestNode;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DynamicNode;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.extension.ExtendWith;
import org.probato.Probato;
import org.probato.engine.junit.DisabledEvaluationResult;
import org.probato.engine.junit.ProbatoForJUnit5;
import org.probato.engine.junit.SuiteFactoryDisplayNameGenerator;
import org.probato.loader.AnnotationLoader;

@DisplayNameGeneration(SuiteFactoryDisplayNameGenerator.class)
@ExtendWith(ProbatoForJUnit5.class)
public abstract class TestSuite {

	@ExtendWith(DisabledEvaluationResult.class)
	@TestFactory
	@DisplayName("Test cases")
	Stream<DynamicNode> buildTestCase() {

		AnnotationLoader.getDisabled(getClass())
			.ifPresent(disabled -> Assumptions.assumeFalse(Boolean.TRUE, disabled.value()));

		return Probato.getTestsCase(getClass())
				.sorted((scriptClazzA, scriptClazzB) -> {
					var scriptA = AnnotationLoader.getScript(scriptClazzA).get();
					var scriptB = AnnotationLoader.getScript(scriptClazzB).get();
					return scriptA.code().compareTo(scriptB.code());
				})
				.filter(scriptClazz -> !AnnotationLoader.isIgnore(scriptClazz))
				.map(this::buildScriptTestNode);
	}

	DynamicNode buildScriptTestNode(Class<?> scriptClazz) {
		return Probato.loadScript(scriptClazz)
			.map(item -> createScriptTestNode(scriptClazz, buildDatasetTestNode(scriptClazz)))
			.filter(item -> !AnnotationLoader.isIgnore(scriptClazz))
			.orElse(createScriptTestNode(scriptClazz, buildDatasetTestNode(scriptClazz)));
	}

	List<DynamicNode> buildDatasetTestNode(Class<?> scriptClazz) {
		return Probato.loadDataset(scriptClazz)
			.map(dataset -> buildDatasetTestNode(scriptClazz, dataset))
			.orElse(buildTestNode(scriptClazz, 0));
	}

	List<DynamicNode> buildDatasetTestNode(Class<?> scriptClazz, Dataset dataset) {

		var list = new ArrayList<DynamicNode>();
		IntStream.rangeClosed(1, Probato.getCsvCounterLines(dataset))
			.forEach(numberLine -> list
				.add(createDatasetTestNode(scriptClazz, numberLine, buildTestNode(scriptClazz, numberLine))));

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