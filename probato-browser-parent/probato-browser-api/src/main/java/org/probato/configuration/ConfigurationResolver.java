package org.probato.configuration;

import java.text.MessageFormat;
import java.util.Optional;

public final class ConfigurationResolver {

	private ConfigurationResolver() {}

	public static int currentExecutionIndex() {
		return Integer.parseInt(executionProperty("execution.current").orElse("-1"));
	}

	public static Optional<String> browserProperty(int index, String key) {
		return Optional.ofNullable(System.getProperty(MessageFormat.format("browsers.[{0}].{1}", index, key)));
	}

	public static Optional<String> executionProperty(String key) {
		return Optional.ofNullable(System.getProperty(key));
	}

}