package org.probato.engine.junit.node;

import static org.junit.jupiter.api.DynamicContainer.dynamicContainer;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

import java.lang.reflect.Method;
import java.net.URI;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.DynamicNode;
import org.probato.loader.AnnotationLoader;
import org.probato.model.Browser;
import org.probato.type.DimensionMode;

public class BrowserTestNode extends TestNode {

	private static final String TEXT = "{0} - {1}";
	private static final String TEXT_DIMENSION = "{0} - {1} ({2}x{3})";

	private final Class<?> suiteClazz;
	private final Class<?> scriptClazz;
	private final Browser browser;
	private final Integer datasetLine;

	public BrowserTestNode(Browser browser, Class<?> suiteClazz, Class<?> scriptClazz, Integer datasetLine) {
		this.browser = browser;
		this.suiteClazz = suiteClazz;
		this.scriptClazz = scriptClazz;
		this.datasetLine = datasetLine;
	}

	@Override
	protected URI getURI() {
		return AnnotationLoader.getProceduresMethod(scriptClazz)
				.stream()
				.map(method -> URI.create(
						"method:"
						.concat(scriptClazz.getName())
						.concat("#")
						.concat(buildMethodSignature(method))))
				.findFirst()
				.orElse(null);
	}

	@Override
	protected DynamicNode createNode() {
		return dynamicContainer(
				buildText(),
				getURI(),
				Stream.of(dynamicTest(
						buildText(),
						getURI(),
						buildTestExecutable())));
	}

	private String buildText() {

		String text = null;

		var description = browser.getType().description();
		var dimension = browser.getDimension();
		if (DimensionMode.CUSTOM.equals(dimension.getMode())) {

			text = buildText(TEXT_DIMENSION,
					description,
					dimension.getMode().description(),
					dimension.getWidth(),
					dimension.getHeight());

		} else {
			text = buildText(TEXT, description, dimension.getMode().description());
		}

		return text;
	}

	private TestNodeExecutable buildTestExecutable() {
		return new TestNodeExecutable(browser, suiteClazz, scriptClazz, datasetLine);
	}

	private String buildMethodSignature(Method method) {
		return new StringBuilder( method.getName())
				.append("(")
				.append(Arrays
					.stream(method.getParameterTypes())
					.map(this::toJvmName)
					.collect(Collectors.joining(",")))
				.append(")")
				.toString();
	}

	private String toJvmName(Class<?> type) {
		if (type.isArray()) {
			return toJvmName(type.getComponentType()) + "[]";
		}
		return type.isPrimitive()
				? type.getName()
				: type.getCanonicalName();
	}

}