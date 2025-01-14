package org.probato.validator;

import java.util.stream.Stream;

import org.probato.api.Suite;
import org.probato.datasource.DatasourceService;
import org.probato.exception.IntegrityException;
import org.probato.loader.AnnotationLoader;
import org.probato.model.type.ComponentValidatorType;
import org.probato.util.StringUtil;

public class SuiteComponentValidator extends ComponentValidator {

	private static final Integer SUITE_ID_MIN_LEN = 1;
	private static final Integer SUITE_ID_MAX_LEN = 20;
	private static final Integer SUITE_NAME_MIN_LEN = 3;
	private static final Integer SUITE_NAME_MAX_LEN = 60;
	private static final Integer SUITE_DESCRIPTION_MAX_LEN = 2000;
	private static final String SUITE_ANNOTATION_REQUIRED = "Class must be annotated with `@Suite`: ''{0}''";
	private static final String SUITE_ID_LENGTH_MSG = "Suite ID must be between {1} and {2} characters long: ''{0}''";
	private static final String SUITE_ID_SPECIAL_CHAR_MSG = "Suite ID should contain only letters and numbers: ''{0}''";
	private static final String SUITE_NAME_LENGTH_MSG = "Suite name must be between {1} and {2} characters long: ''{0}''";
	private static final String SUITE_DESCRIPTION_MAX_LENGTH_MSG = "Suite description must not be more than {1} characters in length: ''{0}''";
	private static final String SUITE_CAN_TEST_CASE_MSG = "Suite must have at least 1 test case: ''{0}''";
	private static final String SUITE_NOT_HAVE_SQL_IMPL_MSG = "Suite has @SQL or @SQLs annotation declares, although does not have any implementation for datasource service: ''{0}''";

	@Override
	public ComponentValidatorType getStrategy() {
		return ComponentValidatorType.SUITE;
	}

	@Override
	public boolean accepted(ComponentValidatorType type) {
		return getStrategy().equals(type);
	}

	@Override
	public void execute(Class<?> suiteClazz) {

		boolean ignored = AnnotationLoader.isIgnore(suiteClazz);
		if (ignored) return;

		var suite = AnnotationLoader.getSuite(suiteClazz)
			.orElseThrow(() -> new IntegrityException(SUITE_ANNOTATION_REQUIRED, suiteClazz.getName()));
		
		if (hasSql(suiteClazz) && !hasSqlImpl()) {
			throw new IntegrityException(SUITE_NOT_HAVE_SQL_IMPL_MSG, getName(suiteClazz));
		}

		validateId(suite, suiteClazz);
		validateName(suite, suiteClazz);
		validateDescription(suite, suiteClazz);
		validateTestCase(suiteClazz);

		chain(suiteClazz);
	}

	private void validateId(Suite suite, Class<?> suiteClazz) {
		var code = suite.code();
		if (isValidIdMinLength(code) || isValidIdMaxLength(code)) {
			throw new IntegrityException(SUITE_ID_LENGTH_MSG, getName(suiteClazz), SUITE_ID_MIN_LEN, SUITE_ID_MAX_LEN);
		}

		if (StringUtil.containsSpecialCharacter(code)) {
			throw new IntegrityException(SUITE_ID_SPECIAL_CHAR_MSG, getName(suiteClazz));
		}
	}

	private void validateName(Suite suite, Class<?> suiteClazz) {
		var name = suite.name();
		if (isValidNameMinLength(name) || isValidNameMaxLength(name)) {
			throw new IntegrityException(SUITE_NAME_LENGTH_MSG, getName(suiteClazz), SUITE_NAME_MIN_LEN, SUITE_NAME_MAX_LEN);
		}
	}

	private void validateDescription(Suite suite, Class<?> suiteClazz) {
		var description = suite.description();
		if (isValidDescritpionMaxLength(description)) {
			throw new IntegrityException(SUITE_DESCRIPTION_MAX_LENGTH_MSG, getName(suiteClazz), SUITE_DESCRIPTION_MAX_LEN);
		}
	}

	private void validateTestCase(Class<?> suiteClazz) {
		if (!hasTestCase(suiteClazz)) {
			throw new IntegrityException(SUITE_CAN_TEST_CASE_MSG, getName(suiteClazz));
		}
	}

	private boolean hasTestCase(Class<?> suiteClazz) {
		return Stream.of(suiteClazz.getDeclaredFields()).anyMatch(AnnotationLoader::isTestCase);
	}

	private boolean hasSql(Class<?> suiteClazz) {
		return Stream.of(suiteClazz).anyMatch(AnnotationLoader::hasSql);
	}

	private boolean hasSqlImpl() {
		return DatasourceService.hasImplementation();
	}

	private boolean isValidIdMinLength(String id) {
		return id.length() < SUITE_ID_MIN_LEN;
	}

	private boolean isValidIdMaxLength(String id) {
		return id.length() > SUITE_ID_MAX_LEN;
	}

	private boolean isValidNameMinLength(String name) {
		return name.length() < SUITE_NAME_MIN_LEN;
	}

	private boolean isValidNameMaxLength(String name) {
		return name.length() > SUITE_NAME_MAX_LEN;
	}

	private boolean isValidDescritpionMaxLength(String description) {
		return description.length() > SUITE_DESCRIPTION_MAX_LEN;
	}
	
	private String getName(Class<?> scritpClazz) {
		return scritpClazz.getName();
	}
}