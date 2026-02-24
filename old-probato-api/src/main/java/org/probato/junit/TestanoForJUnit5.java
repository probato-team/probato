package org.probato.junit;

import java.lang.reflect.AnnotatedElement;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.jupiter.api.extension.ExecutionCondition;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.probato.Probato;

public final class TestanoForJUnit5
		implements BeforeAllCallback, BeforeEachCallback, AfterEachCallback, AfterAllCallback, ExecutionCondition {

	@Override
	public void beforeAll(ExtensionContext context) throws Exception {
		context.getTestClass()
			.ifPresent(Probato::init);
	}
	
	@Override
	public void beforeEach(ExtensionContext context) throws Exception {
		Probato.setup();
	}
	
	@Override
	public void afterEach(ExtensionContext context) throws Exception {
		Probato.finish();
	}

	@Override
	public void afterAll(ExtensionContext context) throws Exception {
		context.getTestClass()
			.ifPresent(clazz -> Probato.destroy());
	}

	@Override
	public ConditionEvaluationResult evaluateExecutionCondition(ExtensionContext context) {

		ConditionEvaluationResult result = null;
		var element = context.getElement();
		if (element.isPresent() && isDisabled(element.get())) {

			result = getDisabled(element.get())
				.map(Disabled::value)
				.map(ConditionEvaluationResult::disabled)
				.orElse(ConditionEvaluationResult.disabled(""));

		} else {
			result = ConditionEvaluationResult.enabled("");
		}

		return result;
	}

	private boolean isDisabled(AnnotatedElement element) {
		return element.isAnnotationPresent(Disabled.class);
	}

	private Optional<Disabled> getDisabled(AnnotatedElement element) {
		return Optional.ofNullable(element.getAnnotation(Disabled.class));
	}

}