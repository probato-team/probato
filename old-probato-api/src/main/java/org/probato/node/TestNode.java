package org.probato.node;

import java.net.URI;
import java.text.MessageFormat;

import org.junit.jupiter.api.DynamicNode;

abstract class TestNode {

	public DynamicNode create() {
		return createNode();
	}

	protected abstract DynamicNode createNode();

	protected abstract URI getURI();

	protected String buildText(String pattern, Object... params) {
		return MessageFormat.format(pattern, params);
	}

}