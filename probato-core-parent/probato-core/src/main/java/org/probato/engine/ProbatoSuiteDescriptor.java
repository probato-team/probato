package org.probato.engine;

import java.text.MessageFormat;

import org.junit.platform.engine.UniqueId;
import org.junit.platform.engine.support.descriptor.AbstractTestDescriptor;
import org.probato.loader.AnnotationLoader;

public class ProbatoSuiteDescriptor extends AbstractTestDescriptor {

	private static final String DISPLAY_NAME = "{0} - {1}";

	private final Class<?> testClass;

	public ProbatoSuiteDescriptor(UniqueId id, Class<?> clazz) {
		super(id, buildDisplayName(clazz));
		this.testClass = clazz;
	}

	public Class<?> getTestClass() {
		return testClass;
	}

	@Override
	public Type getType() {
		return Type.CONTAINER;
	}

	public static String buildDisplayName(Class<?> clazz) {
		return AnnotationLoader.getSuite(clazz)
				.map(suite -> MessageFormat.format(DISPLAY_NAME, suite.code(), suite.name()))
				.orElse(clazz.getSimpleName());
	}

}