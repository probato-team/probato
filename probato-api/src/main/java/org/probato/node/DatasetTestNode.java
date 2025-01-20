package org.probato.node;

import static org.junit.jupiter.api.DynamicContainer.dynamicContainer;

import java.net.URI;
import java.util.stream.Stream;

import org.junit.jupiter.api.DynamicNode;

public class DatasetTestNode extends TestNode {

	private static final String TEXT = "Dataset {0}";

	private final int numberLine;
	private final Stream<? extends DynamicNode> tests;

	public DatasetTestNode(int numberLine, Stream<? extends DynamicNode> tests) {
		this.numberLine = numberLine;
		this.tests = tests;
	}

	@Override
	protected URI getURI() {
		return null;
	}

	@Override
	protected DynamicNode createNode() {
		return dynamicContainer(buildText(TEXT, numberLine), getURI(), tests);
	}

}