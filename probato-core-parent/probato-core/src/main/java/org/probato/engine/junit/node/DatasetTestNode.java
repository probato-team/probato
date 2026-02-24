package org.probato.engine.junit.node;

import static org.junit.jupiter.api.DynamicContainer.dynamicContainer;

import java.net.URI;
import java.util.Optional;
import java.util.stream.Stream;

import org.junit.jupiter.api.DynamicNode;

public class DatasetTestNode extends TestNode {

	private static final String TEXT = "Dataset {0}";

	private final Class<?> scriptClazz;
	private final int numberLine;
	private final Stream<? extends DynamicNode> nodes;

	public DatasetTestNode(Class<?> scriptClazz, int numberLine, Stream<? extends DynamicNode> nodes) {
		this.scriptClazz = scriptClazz;
		this.numberLine = numberLine;
		this.nodes = nodes;
	}

	@Override
	protected URI getURI() {
		return Optional
				.ofNullable(scriptClazz)
				.map(clazz -> URI.create("class:" + clazz.getName()))
				.orElse(null);
	}

	@Override
	protected DynamicNode createNode() {
		return dynamicContainer(
				buildText(
					TEXT,
					numberLine),
				getURI(),
				nodes);
	}

}