package org.probato.validator;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

import org.probato.exception.IntegrityException;
import org.probato.loader.AnnotationLoader;
import org.probato.model.Datamodel;
import org.probato.model.type.ComponentValidatorType;

public class ProcedureComponentValidator extends ComponentValidator {

	private static final String PROCEDURE_TYPE_NON_RUN_METHOD_MSG = "Procedure must have method annotated with @Run: ''{0}''";
	private static final String PROCEDURE_METHOD_INVALID_PARAM_MSG = "Test procedure method can only have a parameter if there is a Dataset for script: ''{0}.{1}''";
	private static final String PARAM_TYPE_INVALID_MSG = "Method must receive parameters of the DataModel type only: ''{0}.{1}''";

	@Override
	public ComponentValidatorType getStrategy() {
		return ComponentValidatorType.PROCEDURE;
	}

	@Override
	public boolean accepted(ComponentValidatorType type) {
		return getStrategy().equals(type);
	}

	@Override
	public void execute(Class<?> suiteClazz) {

		boolean ignored = AnnotationLoader.isIgnore(suiteClazz);
		if (ignored) return;
	
		AnnotationLoader.getTestCaseField(suiteClazz)
			.stream()
			.map(Field::getType)
			.forEach(this::validateScript);

		chain(suiteClazz);
	}
	
	private void validateScript(Class<?> scriptClazz) {

		var ignored = AnnotationLoader.isIgnore(scriptClazz);
		if (ignored) return;

		AnnotationLoader.getPreconditions(scriptClazz)
			.stream()
			.forEach(procedureClazz -> validateProcedure(scriptClazz, procedureClazz, AnnotationLoader.getRunMethod(procedureClazz)));

		AnnotationLoader.getPreconditionsMethod(scriptClazz)
			.stream()
			.forEach(procedureMethod -> validateProcedure(scriptClazz, procedureMethod.getDeclaringClass(), procedureMethod));

		AnnotationLoader.getProcedures(scriptClazz)
			.stream()
			.forEach(procedureClazz -> validateProcedure(scriptClazz, procedureClazz, AnnotationLoader.getRunMethod(procedureClazz)));

		AnnotationLoader.getProceduresMethod(scriptClazz)
			.stream()
			.forEach(procedureMethod -> validateProcedure(scriptClazz, procedureMethod.getDeclaringClass(), procedureMethod));

		AnnotationLoader.getPostconditions(scriptClazz)
			.stream()
			.forEach(procedureClazz -> validateProcedure(scriptClazz, procedureClazz, AnnotationLoader.getRunMethod(procedureClazz)));

		AnnotationLoader.getPostconditionsMethod(scriptClazz)
			.stream()
			.forEach(procedureMethod -> validateProcedure(scriptClazz, procedureMethod.getDeclaringClass(), procedureMethod));
	}
	
	private void validateProcedure(Class<?> scriptClazz, Class<?> procedureClazz, Method procedureMethod) {

		var ignored = AnnotationLoader.isIgnore(procedureClazz) || AnnotationLoader.isIgnore(procedureMethod) ;
		if (ignored) return;
		
		if (Objects.isNull(procedureMethod)) {
			throw new IntegrityException(PROCEDURE_TYPE_NON_RUN_METHOD_MSG, procedureClazz.getName());
		}

		if (hasParams(procedureMethod) && !AnnotationLoader.hasDataset(scriptClazz)) {
			throw new IntegrityException(PROCEDURE_METHOD_INVALID_PARAM_MSG, procedureClazz.getName(), procedureMethod.getName());
		}

		Arrays.asList(procedureMethod.getParameterTypes())
			.forEach(clazz -> {
				if (!isValidClass(clazz)) {
					throw new IntegrityException(
						PARAM_TYPE_INVALID_MSG, 
						procedureMethod.getDeclaringClass().getName(), 
						procedureMethod.getName());
				}
			});
	}
	
	private boolean hasParams(Method method) {
		return method.getParameterCount() > 0;
	}

	private boolean isValidClass(Class<?> paramClazz) {
		return Datamodel.class.isAssignableFrom(paramClazz)
			|| (Objects.nonNull(paramClazz.getSuperclass()) && isValidClass(paramClazz.getSuperclass()));
	}

}