package org.probato.validator;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import org.probato.api.Param;
import org.probato.exception.IntegrityException;
import org.probato.loader.AnnotationLoader;
import org.probato.model.PageObject;
import org.probato.model.type.ComponentValidatorType;

public class PageObjectComponentValidator extends ComponentValidator {

	private static final String PAGE_INVALID_MSG = "Class must extend the PageObject class: ''{0}''";
	private static final String PAGE_METH_ACT_INVALID_MSG = "Method must have all parameters annotated with '@Param': ''{0}.{1}''";

	@Override
	public ComponentValidatorType getStrategy() {
		return ComponentValidatorType.PAGE_OBJECT;
	}

	@Override
	public boolean accepted(ComponentValidatorType type) {
		return getStrategy().equals(type);
	}

	@Override
	public void execute(Class<?> suiteClazz) {
		
		boolean ignored = AnnotationLoader.isIgnore(suiteClazz);
		if (ignored) return;

		AnnotationLoader.getTestCaseField(suiteClazz).stream()
			.map(Field::getType)
			.forEach(this::validateScript);
		
		chain(suiteClazz);
	}
	
	private void validateScript(Class<?> scriptClazz) {

		var ignored = AnnotationLoader.isIgnore(scriptClazz);
		if (ignored) return;
		
		AnnotationLoader.getPagesObject(scriptClazz)
			.forEach(field -> {
				validateClass(field.getType());
				getActions(field.getType())
					.forEach(method -> validateMethod(field.getType(), method));
			});

		AnnotationLoader.getPreconditions(scriptClazz)
			.stream()
			.forEach(this::validateProcedure);
		
		AnnotationLoader.getPreconditionsMethod(scriptClazz)
			.stream()
			.forEach(procedureMethod -> validateProcedure(procedureMethod.getDeclaringClass()));
		
		AnnotationLoader.getProcedures(scriptClazz)
			.stream()
			.forEach(this::validateProcedure);
		
		AnnotationLoader.getProceduresMethod(scriptClazz)
			.stream()
			.forEach(procedureMethod -> validateProcedure(procedureMethod.getDeclaringClass()));
		
		AnnotationLoader.getPostconditions(scriptClazz)
			.stream()
			.forEach(this::validateProcedure);
		
		AnnotationLoader.getPostconditionsMethod(scriptClazz)
			.stream()
			.forEach(procedureMethod -> validateProcedure(procedureMethod.getDeclaringClass()));
	}
	
	private void validateProcedure(Class<?> procedureClazz) {
		AnnotationLoader.getPagesObject(procedureClazz)
			.forEach(field -> {
				validateClass(field.getType());
				getActions(field.getType())
					.forEach(method -> validateMethod(field.getType(), method));
			});
	}
	
	private void validateClass(Class<?> pageClazz) {
		if (!PageObject.class.isAssignableFrom(pageClazz)) {
			throw new IntegrityException(PAGE_INVALID_MSG, pageClazz.getName());
		}
	}

	private List<Method> getActions(Class<?> pageClazz) {
		return AnnotationLoader.getActionMethods(pageClazz);
	}

	private void validateMethod(Class<?> pageClazz, Method method) {
		var action = AnnotationLoader.getActionValue(method);
		if (action.contains("{{") && action.contains("}}")) {
			Arrays.asList(method.getParameters())
				.forEach(param -> {
					if (!param.isAnnotationPresent(Param.class)) {
						throw new IntegrityException(PAGE_METH_ACT_INVALID_MSG, pageClazz.getName(), method.getName());
					}
				});
		}
	}

}