package org.probato.node;

import java.net.URI;

import org.junit.jupiter.api.DynamicNode;
import org.junit.jupiter.api.DynamicTest;

public class DesktopTestNode extends TestNode {

	private static final String TEXT = "Desktop";

	private final Class<?> suiteClazz;
	private final Class<?> scriptClazz;
	private final Integer datasetLine;

	public DesktopTestNode(Class<?> suiteClazz, Class<?> scriptClazz, Integer datasetLine) {
		this.suiteClazz = suiteClazz;
		this.scriptClazz = scriptClazz;
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
		return TEXT;
	}

	private TestNodeExecutable buildTestExecutable() {
		return new TestNodeExecutable(suiteClazz, scriptClazz, datasetLine);
	}

}