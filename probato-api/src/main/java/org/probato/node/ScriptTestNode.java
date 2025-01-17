package org.probato.node;

import static org.junit.jupiter.api.DynamicContainer.dynamicContainer;

import java.net.URI;
import java.util.Optional;
import java.util.stream.Stream;

import org.junit.jupiter.api.DynamicNode;
import org.probato.api.Script;

public class ScriptTestNode extends TestNode {

	private static final String SCRITP_TEXT = "{0} - {1}";
	private static final String CLAZZ_TEXT = "{0}";

	private final Class<?> scriptClazz;
	private final Script script;
	private final Stream<? extends DynamicNode> tests;

	public ScriptTestNode(Script script, Stream<? extends DynamicNode> tests) {
		this.scriptClazz = null;
		this.script = script;
		this.tests = tests;
	}

	public ScriptTestNode(Class<?> scriptClazz, Stream<? extends DynamicNode> tests) {
		this.scriptClazz = scriptClazz;
		this.script = null;
		this.tests = tests;
	}

	@Override
	protected URI getURI() {
		return null;
	}

	@Override
	protected DynamicNode createNode() {
		return Optional.ofNullable(script)
				.map(item -> dynamicContainer(buildText(SCRITP_TEXT, item.code(), item.name()), getURI(), tests))
				.orElse(dynamicContainer(buildText(CLAZZ_TEXT, scriptClazz), getURI(), tests));
	}

}