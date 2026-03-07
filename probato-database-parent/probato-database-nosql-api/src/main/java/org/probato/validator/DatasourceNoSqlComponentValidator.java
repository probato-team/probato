package org.probato.validator;

import java.lang.reflect.Field;
import java.util.Objects;

import org.probato.exception.IntegrityException;
import org.probato.loader.AnnotationLoader;
import org.probato.loader.ConfigurationContext;
import org.probato.loader.NoSqlLoader;
import org.probato.type.ComponentValidatorType;
import org.probato.type.DatasourceType;
import org.probato.utils.CassandraUtils;
import org.probato.utils.MongoDbUtils;
import org.probato.utils.StringUtils;

public class DatasourceNoSqlComponentValidator extends ComponentValidator {

	private static final String DATASOURCE_NAME_REQUIRED = "Datasource name must be required in the '@NoSQL' annotation: ''{0}''";
	private static final String DATASOURCE_NOT_FOUND = "Datasource ''{0}'' not fount";
	private static final String DATASOURCE_URL_REQUIRED = "Datasource ''{0}.url'' must be required in the '@NoSQL' annotation: ''{1}''";
	private static final String DATASOURCE_TYPE_REQUIRED = "Datasource ''{0}.type'' must be required in the '@NoSQL' annotation: ''{1}''";
	private static final String DATASOURCE_DATABASE_REQUIRED = "Datasource ''{0}.database'' must be required in the '@NoSQL' annotation: ''{1}''";
	private static final String DATASOURCE_USERNAME_REQUIRED = "Datasource ''{0}.username'' must be required in the '@NoSQL' annotation: ''{1}''";
	private static final String DATASOURCE_PASSWORD_REQUIRED = "Datasource ''{0}.password'' must be required in the '@NoSQL' annotation: ''{1}''";
	private static final String DATASOURCE_VALIDATE_CONECTION = "Datasource ''{0}'' Invalid connection validation: ''{1}''";

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

		NoSqlLoader.getNoSqlPaths(suiteClazz).entrySet()
				.forEach(datasource -> {

					boolean ignored = AnnotationLoader.isIgnore(suiteClazz);
					if (ignored) return;

					validateName(suiteClazz, datasource.getKey());
					validateDatasource(suiteClazz, datasource.getKey());
				});

		AnnotationLoader.getTestCaseField(suiteClazz).stream()
				.map(Field::getType)
				.forEach(scriptClazz -> {

					boolean ignored = AnnotationLoader.isIgnore(suiteClazz);
					if (ignored) return;

					NoSqlLoader.getNoSqlPaths(suiteClazz).entrySet()
						.forEach(datasource -> {
							validateName(scriptClazz, datasource.getKey());
							validateDatasource(scriptClazz, datasource.getKey());
						});
				});

		chain(suiteClazz);
	}

	private void validateName(Class<?> clazz, String datasourceName) {
		if (StringUtils.isBlank(datasourceName)) {
			throw new IntegrityException(DATASOURCE_NAME_REQUIRED, getName(clazz));
		}
	}

	private void validateDatasource(Class<?> clazz, String datasourceName) {

		var configuration = ConfigurationContext.get(clazz);
		var datasource = configuration.getDatasource(datasourceName);
		if (Objects.isNull(datasource)) {
			throw new IntegrityException(DATASOURCE_NOT_FOUND, datasourceName);
		}

		if (StringUtils.isBlank(datasource.getUrl())) {
			throw new IntegrityException(DATASOURCE_URL_REQUIRED, datasourceName, getName(clazz));
		}

		if (Objects.isNull(datasource.getType())) {
			throw new IntegrityException(DATASOURCE_TYPE_REQUIRED, datasourceName, getName(clazz));
		}

		if (StringUtils.isBlank(datasource.getDatabase())) {
			throw new IntegrityException(DATASOURCE_DATABASE_REQUIRED, datasourceName, getName(clazz));
		}

		if (StringUtils.isBlank(datasource.getUsername())) {
			throw new IntegrityException(DATASOURCE_USERNAME_REQUIRED, datasourceName, getName(clazz));
		}

		if (StringUtils.isBlank(datasource.getPassword())) {
			throw new IntegrityException(DATASOURCE_PASSWORD_REQUIRED, datasourceName, getName(clazz));
		}

		try {

			if (DatasourceType.MONGODB.equals(datasource.getType())) {
				MongoDbUtils.validateConnection(
						datasource.getUrl(),
						datasource.getDatabase(),
						datasource.getUsername(),
						datasource.getPassword());
			}

			if (DatasourceType.CASSANDRA.equals(datasource.getType())) {
				CassandraUtils.validateConnection(
						datasource.getUrl(),
						datasource.getDatabase(),
						datasource.getUsername(),
						datasource.getPassword());
			}

		} catch (Exception ex) {
			throw new IntegrityException(DATASOURCE_VALIDATE_CONECTION, datasourceName, ex.getMessage());
		}
	}

	private String getName(Class<?> scritpClazz) {
		return scritpClazz.getName();
	}

}