package org.probato.validator;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;

import org.probato.exception.IntegrityException;
import org.probato.loader.AnnotationLoader;
import org.probato.loader.ConfigurationContext;
import org.probato.loader.NoSqlLoader;
import org.probato.type.ComponentValidatorType;
import org.probato.type.DatasourceType;
import org.probato.utils.FileUtils;
import org.probato.utils.MongoDbUtils;
import org.probato.utils.StringUtils;

public class MongoDbNoSqlComponentValidator extends ComponentValidator {

	private static final String NOSQL_LIST_ANNOTATION_REQUIRED = "List of nosql files must have at least 1 item in the '@NoSQL' annotation: ''{0}''";
	private static final String NOSQL_VALUE_ANNOTATION_REQUIRED = "List of nosql files must not have null or empty value in the '@NoSQL' annotation: ''{0}''";
	private static final String NOSQL_FILE_NOT_FOUNT = "NoSQL file ''{0}'' not found: ''{1}''";
	private static final String NOSQL_LOAD_FILE = "Problem when trying to load NoSQL file: ''{0}''";

	@Override
	public ComponentValidatorType getStrategy() {
		return ComponentValidatorType.DATASOURCE;
	}

	@Override
	public boolean accepted(ComponentValidatorType type) {
		return getStrategy().equals(type);
	}

	@Override
	public void execute(Class<?> suiteClazz) {

		boolean ignored = AnnotationLoader.isIgnore(suiteClazz);
		if (ignored) return;

		validateNoSQL(suiteClazz);

		AnnotationLoader.getTestCaseField(suiteClazz)
			.stream()
			.map(Field::getType)
			.forEach(this::validateScript);

		chain(suiteClazz);
	}

	private void validateScript(Class<?> scriptClazz) {

		var ignored = AnnotationLoader.isIgnore(scriptClazz);
		if (ignored) return;

		validateNoSQL(scriptClazz);

		AnnotationLoader.getPreconditions(scriptClazz)
			.stream()
			.forEach(this::validateProcedure);

		AnnotationLoader.getProcedures(scriptClazz)
			.stream()
			.forEach(this::validateProcedure);

		AnnotationLoader.getPostconditions(scriptClazz)
			.stream()
			.forEach(this::validateProcedure);
	}

	private void validateProcedure(Class<?> procedureClazz) {

		var ignored = AnnotationLoader.isIgnore(procedureClazz);
		if (ignored) return;

		validateNoSQL(procedureClazz);
	}

	private void validateNoSQL(Class<?> clazz) {

		NoSqlLoader.getNoSqlPaths(clazz)
			.entrySet()
			.forEach(entry -> {

				var datasourceName = entry.getKey();
				var datasourceSqls = entry.getValue();

				var configuration = ConfigurationContext.get(clazz);
				var datasource = configuration.getDatasource(datasourceName);
				if (!DatasourceType.MONGODB.equals(datasource.getType())) {
					return ;
				}

				validateNoSqlList(clazz, datasourceSqls);
				datasourceSqls
					.stream()
					.forEach(item -> {
						validateSqlItem(clazz, item);
						validateFile(clazz, item);
						validateSqlCommands(clazz, datasourceName, item);
					});
			});
	}

	private void validateNoSqlList(Class<?> clazz, List<String> sqlList) {
		if (Objects.isNull(sqlList) || sqlList.isEmpty()) {
			throw new IntegrityException(NOSQL_LIST_ANNOTATION_REQUIRED, getName(clazz));
		}
	}

	private void validateSqlItem(Class<?> clazz, String item) {
		if (StringUtils.isBlank(item)) {
			throw new IntegrityException(NOSQL_VALUE_ANNOTATION_REQUIRED, getName(clazz));
		}
	}

	private void validateFile(Class<?> clazz, String item) {
		if (!FileUtils.exists(item)) {
			throw new IntegrityException(NOSQL_FILE_NOT_FOUNT, item, getName(clazz));
		}
	}

	private void validateSqlCommands(Class<?> clazz, String datasourceName, String item) {
		try {

			var configuration = ConfigurationContext.get(clazz);
			var datasource = configuration.getDatasource(datasourceName);
			var commands = MongoDbUtils.getDocuments(item);
			MongoDbUtils.validateDocuments(
					datasource.getUrl(),
					datasource.getDatabase(),
					datasource.getUsername(),
					datasource.getPassword(),
					commands);

		} catch (IOException ex) {
			throw new IntegrityException(NOSQL_LOAD_FILE, ex.getMessage());
		}
	}

	private String getName(Class<?> clazz) {
		return clazz.getName();
	}
}