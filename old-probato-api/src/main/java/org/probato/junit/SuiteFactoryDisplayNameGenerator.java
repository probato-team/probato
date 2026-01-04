package org.probato.junit;

import static org.junit.jupiter.api.DisplayNameGenerator.parameterTypesAsString;

import java.lang.reflect.Method;
import java.text.MessageFormat;

import org.junit.jupiter.api.DisplayNameGenerator;
import org.probato.loader.AnnotationLoader;

public final class SuiteFactoryDisplayNameGenerator implements DisplayNameGenerator {

	private static final String SUITE_TEXT = "{0} - {1}";

	@Override
	public String generateDisplayNameForClass(Class<?> suiteClazz) {
		return AnnotationLoader.getSuite(suiteClazz)
				.map(suite -> buildText(SUITE_TEXT, suite.code(), suite.name()))
				.orElse(suiteClazz.getSimpleName());
	}

	@Override
	public String generateDisplayNameForNestedClass(Class<?> nestedClass) {
		return nestedClass.getSimpleName();
	}

	@Override
	public String generateDisplayNameForMethod(Class<?> testClass, Method testMethod) {
		return testMethod.getName() + parameterTypesAsString(testMethod);
	}

	private String buildText(String pattern, Object... params) {
		return MessageFormat.format(pattern, params);
	}

}