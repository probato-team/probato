package org.probato.engine.junit;

import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.jupiter.api.extension.ExecutionCondition;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.probato.loader.AnnotationLoader;

public class DisabledEvaluationResult implements ExecutionCondition {

	@Override
	public ConditionEvaluationResult evaluateExecutionCondition(ExtensionContext context) {
		return !isDisabled(context)
	            ? ConditionEvaluationResult.enabled("OK")
	            : ConditionEvaluationResult.disabled("Desabilitado dinamicamente");
	}

	private boolean isDisabled(ExtensionContext context) {
		return context.getTestClass()
			.map(AnnotationLoader::isDisabled)
			.orElse(Boolean.FALSE);
	}

}
