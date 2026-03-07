package org.probato.test.util;

import java.util.Optional;

import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.jupiter.api.extension.ExecutionCondition;
import org.junit.jupiter.api.extension.ExtensionContext;

public class IgnoreIfWorkflowCondition implements ExecutionCondition {

	@Override
	public ConditionEvaluationResult evaluateExecutionCondition(ExtensionContext context) {
		var enableWorkFlow = isEnable(context);
		return enableWorkFlow
				? ConditionEvaluationResult.disabled("Test disabled when workflow")
				: ConditionEvaluationResult.enabled("");
	}

	private boolean isEnable(ExtensionContext context) {
		var isWorkflow = Optional.ofNullable(System.getProperty("workflow"))
				.map(val -> Boolean.TRUE)
				.orElse(Boolean.FALSE);
		return isWorkflow && (context.getTestMethod().isPresent() || context.getTestClass().isPresent());
	}

}