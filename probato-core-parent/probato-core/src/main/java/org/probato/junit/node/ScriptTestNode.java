package org.probato.junit.node;

import static org.junit.jupiter.api.DynamicContainer.dynamicContainer;

import java.net.URI;
import java.util.Optional;
import java.util.stream.Stream;

import org.junit.jupiter.api.DynamicNode;
import org.probato.api.Script;
import org.probato.loader.AnnotationLoader;

public class ScriptTestNode extends TestNode {

	private static final String SCRITP_TEXT = "{0} - {1}";
	private static final String CLAZZ_TEXT = "{0}";

	private final Class<?> scriptClazz;
	private final Script script;
	private final Stream<? extends DynamicNode> nodes;

	public ScriptTestNode(Script script, Class<?> scriptClazz,  Stream<? extends DynamicNode> nodes) {
		this.scriptClazz = scriptClazz;
		this.script = script;
		this.nodes = nodes;
	}

	public ScriptTestNode(Class<?> scriptClazz, Stream<? extends DynamicNode> nodes) {
		this.scriptClazz = scriptClazz;
		this.script = AnnotationLoader.getScript(scriptClazz).orElse(null);
		this.nodes = nodes;
	}

	@Override
	protected URI getURI() {
		return Optional.ofNullable(scriptClazz)
				.map(clazz -> URI.create("class:" + clazz.getName()))
				.orElse(null);
	}

	@Override
	protected DynamicNode createNode() {
		return Optional
				.ofNullable(script)
				.map(item -> dynamicContainer(
						buildText(
							SCRITP_TEXT,
							item.code(),
							item.name()),
						getURI(),
						nodes))
				.orElse(dynamicContainer(buildText(CLAZZ_TEXT, scriptClazz), getURI(), nodes));
	}

	public Class<?> getScriptClazz() {
		return scriptClazz;
	}

}