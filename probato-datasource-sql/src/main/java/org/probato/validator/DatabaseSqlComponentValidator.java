package org.probato.validator;

import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.probato.core.loader.AnnotationLoader;
import org.probato.core.loader.Configuration;
import org.probato.entity.type.ComponentValidatorType;
import org.probato.exception.IntegrityException;
import org.probato.util.FileUtil;
import org.probato.util.SqlUtil;
import org.probato.util.StringUtil;

public class DatabaseSqlComponentValidator extends ComponentValidator {

	private static final String SQL_LIST_ANNOTATION_REQUIRED = "List of sql must have at least 1 item in the '@SQL' annotation: ''{0}''";
	private static final String SQL_VALUE_ANNOTATION_REQUIRED = "List of sql must not have null or empty value in the '@SQL' annotation: ''{0}''";
	private static final String SQL_FILE_NOT_FOUNT = "SQL file ''{0}'' not found: ''{1}''";
	private static final String SQL_LOAD_FILE = "Problem when trying to load SQL file: ''{0}''";
	private static final String SQL_VALIDATE_COMMAND = "Invalid SQL command in file ''{0}'': \n''{1}''";

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
		
		validateSQL(suiteClazz);
		
		AnnotationLoader.getTestCaseField(suiteClazz).stream()
			.map(Field::getType)
			.forEach(this::validateScript);
		
		chain(suiteClazz);
	}
	
	private void validateScript(Class<?> scriptClazz) {
		
		var ignored = AnnotationLoader.isIgnore(scriptClazz);
		if (ignored) return;
		
		validateSQL(scriptClazz);
		
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
		
		validateSQL(procedureClazz);
	}
	
	private void validateSQL(Class<?> clazz) {
		
		AnnotationLoader.getSqlPaths(clazz).entrySet()
			.forEach(datasource -> {
				
				var datasourceName = datasource.getKey();
				var sqlList = datasource.getValue();
				validateSqlList(clazz, sqlList);
				sqlList.stream()
					.forEach(item -> {
						validateSqlItem(clazz, item);
						validateFile(clazz, item);
						validateSqlCommands(clazz, datasourceName, item);
					});
			});
	}

	private void validateSqlList(Class<?> clazz, List<String> sqlList) {
		if (CollectionUtils.isEmpty(sqlList)) {
			throw new IntegrityException(SQL_LIST_ANNOTATION_REQUIRED, getName(clazz));
		}
	}

	private void validateSqlItem(Class<?> clazz, String item) {
		if (StringUtil.isBlank(item)) {
			throw new IntegrityException(SQL_VALUE_ANNOTATION_REQUIRED, getName(clazz));
		}
	}

	private void validateFile(Class<?> clazz, String item) {
		if (!FileUtil.exists(item)) {
			throw new IntegrityException(SQL_FILE_NOT_FOUNT, item, getName(clazz));
		}
	}

	private void validateSqlCommands(Class<?> clazz, String datasourceName, String item) {
		// TODO validate sql syntax only
		/*
		try {

			var configuration = Configuration.getInstance(clazz);
			var datasource = configuration.getDatasource(datasourceName);
			var commands = SqlUtil.getQueries(item);
			SqlUtil.validateQueries(
					datasource.getUrl(), 
					datasource.getUsername(), 
					datasource.getPassword(), 
					datasource.getDriver(),
					datasource.getSchema(),
					commands);

		} catch (IOException | ClassNotFoundException e) {
			throw new IntegrityException(SQL_LOAD_FILE, e.getMessage());
		} catch (SQLException e) {
			throw new IntegrityException(SQL_VALIDATE_COMMAND, item, e.getMessage());
		}
		*/
	}
	
	private String getName(Class<?> clazz) {
		return clazz.getName();
	}
}