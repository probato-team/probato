package org.probato.engine;

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
import org.probato.loader.Configuration;

public class PageProxy implements InvocationHandler {

	private Object target;

	public PageProxy(Object target) {
		this.target = target;
	}	

	@Override
	public Object invoke(Object object, Method method, Object[] args) throws Throwable {

		String actionValue = null;
		String stepValue = null;
		Object result = null;
		
		try {

			if (!ExecutionContextHolder.isCollectionMode()) {
				var configuration = Configuration.getInstance();
				TimeUnit.MILLISECONDS.sleep(configuration.getExecution().getDelay().getActionInterval());
				result = method.invoke(target, args);
			}

		} catch (Throwable ex) {
			
			if (Objects.nonNull(ex.getCause())) {
				ExecutionContextHolder.setThrowable(ex.getCause());
				throw ex.getCause();
			} else {
				ExecutionContextHolder.setThrowable(ex);
				throw ex;
			}

		} finally {
			
			if (method.isAnnotationPresent(Action.class)) {

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

				actionValue = stepValue = AnnotationLoader.getActionValue(method);
				for (Entry<String, String> item: params.entrySet()) {
					stepValue = replaceParam(actionValue, item.getKey(), item.getValue());
				}
			}
		}

		addStepAction(method, actionValue, stepValue);

		return result;
	}
	
	private void addStepAction(Method method, String actionValue, String stepValue) {
		if (method.isAnnotationPresent(Action.class)) {
			ExecutionContextHolder.addAction(actionValue);
			ExecutionContextHolder.addStep(stepValue);
		}
	}

	private static String replaceParam(String content, String key, String value) {
		return replace(content, "\\{\\{%s\\}\\}", key, value);
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