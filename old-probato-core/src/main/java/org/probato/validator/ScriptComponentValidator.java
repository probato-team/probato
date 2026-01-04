package org.probato.validator;

import java.lang.reflect.Field;
import java.util.stream.Stream;

import org.probato.api.Script;
import org.probato.exception.IntegrityException;
import org.probato.loader.AnnotationLoader;
import org.probato.model.type.ComponentValidatorType;
import org.probato.util.StringUtil;

public class ScriptComponentValidator extends ComponentValidator {

	private static final Integer SCRIPT_ID_MIN_LEN = 3;
	private static final Integer SCRIPT_ID_MAX_LEN = 40;
	private static final Integer SCRIPT_NAME_MIN_LEN = 3;
	private static final Integer SCRIPT_NAME_MAX_LEN = 250;
	private static final Integer SCRIPT_DESC_MAX_LENGTH = 2000;
	private static final String SCRIPT_ANNOTATION_REQUIRED = "Class must be annotated with `@Script`: ''{0}''";
	private static final String SCRIPT_ID_LEN_MSG = "Script ID must be between {0} and {1} characters long: ''{2}''";
	private static final String SCRIPT_ID_SPECIAL_CHAR_MSG = "Script ID should contain only letters and numbers: ''{0}''";
	private static final String SCRIPT_NAME_LEN_MSG = "Script name must be between {0} and {1} characters long: ''{2}''";
	private static final String SCRIPT_DESC_MAX_LENGTH_MSG = "Script description must not be more than {0} characters in length: ''{1}''";
	private static final String SCRIPT_CAN_TEST_CASE_MSG = "Script must have at least 1 procedure: ''{0}''";

	@Override
	public ComponentValidatorType getStrategy() {
		return ComponentValidatorType.SCRIPT;
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

		var script = AnnotationLoader.getScript(scriptClazz)
			.orElseThrow(() -> new IntegrityException(SCRIPT_ANNOTATION_REQUIRED, scriptClazz.getName()));

		validateId(script, scriptClazz);
		validateName(script, scriptClazz);
		validateDescription(script, scriptClazz);
		validateProcedure(scriptClazz);
	}
	
	private void validateId(Script script, Class<?> scriptClazz) {
		var code = script.code();
		if (isValidIdMinLength(code) || isValidIdMaxLength(code)) {
			throw new IntegrityException(SCRIPT_ID_LEN_MSG, SCRIPT_ID_MIN_LEN, SCRIPT_ID_MAX_LEN, getName(scriptClazz));
		}

		if (StringUtil.containsSpecialCharacter(code)) {
			throw new IntegrityException(SCRIPT_ID_SPECIAL_CHAR_MSG, getName(scriptClazz));
		}
	}

	private void validateName(Script script, Class<?> scriptClazz) {
		var name = script.name();
		if (isValidNameMinLength(name) || isValidNameMaxLength(name)) {
			throw new IntegrityException(SCRIPT_NAME_LEN_MSG, SCRIPT_NAME_MIN_LEN, SCRIPT_NAME_MAX_LEN, getName(scriptClazz));
		}
	}

	private void validateDescription(Script script, Class<?> scriptClazz) {
		var description = script.description();
		if (isValidDescriptionMaxLength(description)) {
			throw new IntegrityException(SCRIPT_DESC_MAX_LENGTH_MSG, SCRIPT_DESC_MAX_LENGTH, getName(scriptClazz));
		}
	}

	private void validateProcedure(Class<?> scriptClazz) {
		if (!hasMethodProcedure(scriptClazz) && !hasFieldProcedure(scriptClazz)) {
			throw new IntegrityException(SCRIPT_CAN_TEST_CASE_MSG, getName(scriptClazz));
		}
	}

	private boolean hasMethodProcedure(Class<?> scriptClazz) {
		return Stream.of(scriptClazz.getDeclaredMethods()).anyMatch(AnnotationLoader::isProcedure);
	}

	private boolean hasFieldProcedure(Class<?> scriptClazz) {
		return Stream.of(scriptClazz.getDeclaredFields()).anyMatch(AnnotationLoader::isProcedure);
	}
	
	private boolean isValidIdMinLength(String id) {
		return id.length() < SCRIPT_ID_MIN_LEN;
	}

	private boolean isValidIdMaxLength(String id) {
		return id.length() > SCRIPT_ID_MAX_LEN;
	}

	private boolean isValidNameMinLength(String name) {
		return name.length() < SCRIPT_NAME_MIN_LEN;
	}

	private boolean isValidNameMaxLength(String name) {
		return name.length() > SCRIPT_NAME_MAX_LEN;
	}

	private boolean isValidDescriptionMaxLength(String description) {
		return description.length() > SCRIPT_DESC_MAX_LENGTH;
	}
	
	private String getName(Class<?> scritpClazz) {
		return scritpClazz.getName();
	}
}