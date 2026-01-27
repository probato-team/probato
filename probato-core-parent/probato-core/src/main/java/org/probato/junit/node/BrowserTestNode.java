package org.probato.junit.node;

import java.net.URI;

import org.junit.jupiter.api.DynamicNode;
import org.junit.jupiter.api.DynamicTest;
import org.probato.engine.ExecutionContext;
import org.probato.engine.ExecutionEngine;
import org.probato.engine.ExecutionRecord;
import org.probato.model.Browser;
import org.probato.type.DimensionMode;

public class BrowserTestNode extends TestNode {

	private static final String TEXT = "{0} - {1}";
	private static final String TEXT_DIMENSION = "{0} - {1} ({2}x{3})";

	private final Class<?> suiteClazz;
	private final Class<?> scriptClazz;
	private final Browser browser;
	private final Integer datasetLine;

	public BrowserTestNode(Class<?> suiteClazz, Class<?> scriptClazz, Browser browser, Integer datasetLine) {
		this.suiteClazz = suiteClazz;
		this.scriptClazz = scriptClazz;
		this.browser = browser;
		this.datasetLine = datasetLine;
	}

	@Override
	protected URI getURI() {
		return null;
	}

	@Override
	protected DynamicNode createNode() {
		return DynamicTest.dynamicTest(buildText(), getURI(), buildTestExecutable());
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
		return new TestNodeExecutable(suiteClazz, scriptClazz, browser, datasetLine);
	}

}