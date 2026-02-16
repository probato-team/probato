package org.probato.engine.procedure;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import org.probato.api.Action;
import org.probato.api.Param;
import org.probato.loader.AnnotationLoader;
import org.probato.loader.ConfigurationContext;

public class PageProxy implements InvocationHandler {

	private static final String REPLACE_PARAM = "\\{\\{%s\\}\\}";

	private Object target;
	private ExecutionResult result;

	public PageProxy(Object target, ExecutionResult result) {
		this.target = target;
		this.result = result;
	}

	@Override
	public Object invoke(Object object, Method method, Object[] args) throws Throwable {

		String actionValue = null;
		String stepValue = null;
		Object data = null;
		var step = new StepResult(method, result.getCurrentPhase());

		try {

			step.start();

			if (!result.isCollectMode()) {
				var configuration = ConfigurationContext.get();
				TimeUnit.MILLISECONDS.sleep(configuration.getExecution().getDelay().getActionInterval());
				data = method.invoke(target, args);
			}

			step.stop();

		} catch (Throwable ex) { // NOSONAR

			throwsError(step, ex);

		} finally {

			if (method.isAnnotationPresent(Action.class)) {
				actionValue = stepValue = AnnotationLoader.getActionValue(method);
				for (Entry<String, String> item: getParams(method, args).entrySet()) {
					stepValue = replaceParam(actionValue, item.getKey(), item.getValue());
				}
			}
		}

		addStepAction(step, method, actionValue, stepValue);

		return data;
	}

	private HashMap<String, String> getParams(Method method, Object[] args) {
		var params = new HashMap<String, String>();
		var paramAnottations = method.getParameterAnnotations();
		for (int i = 0; i < paramAnottations.length; i++) {
			for (Annotation annotation : paramAnottations[i]) {
				if (annotation instanceof Param) {
					var key = ((Param) annotation).value();
					var valueArg = args[i];
					if (Objects.isNull(valueArg)) {
						valueArg = "[null]";
					}
					params.put(key, valueArg.toString());
				}
			}
		}
		return params;
	}

	private void throwsError(StepResult step, Throwable ex) throws Throwable { // NOSONAR
		if (Objects.nonNull(ex.getCause())) {
			step.error(ex.getCause());
			throw ex.getCause();
		} else {

			step.error(ex);
			throw ex;
		}
	}

	private void addStepAction(StepResult step, Method method, String actionValue, String stepValue) {
		if (method.isAnnotationPresent(Action.class)) {
			if (result.isCollectMode()) {
				step.actionValue(actionValue);
				step.stepValue(stepValue);
				result.addCollectedStep(step);
			} else {
				step.actionValue(actionValue);
				step.stepValue(stepValue);
				result.addExecutedStep(step);
			}
		}
	}

	private static String replaceParam(String content, String key, String value) {
		return replace(content, REPLACE_PARAM, key, value);
	}

	private static String replace(String content, String pattern, String key, String value) {

		var p = Pattern.compile(String.format(pattern, key));
		var m = p.matcher(content);
		var sb = new StringBuffer();
		while (m.find()) {
			m.appendReplacement(sb, value);
		}
		m.appendTail(sb);

		return sb.toString();
	}
}