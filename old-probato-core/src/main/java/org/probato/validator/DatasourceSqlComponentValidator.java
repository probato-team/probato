package org.probato.validator;

import java.lang.reflect.Field;
import java.util.Objects;

import org.probato.exception.IntegrityException;
import org.probato.loader.AnnotationLoader;
import org.probato.loader.Configuration;
import org.probato.model.type.ComponentValidatorType;
import org.probato.util.SqlUtil;
import org.probato.util.StringUtil;

public class DatasourceSqlComponentValidator extends ComponentValidator {

	private static final String DATASOURCE_NAME_REQUIRED = "Datasource name must be required in the '@SQL' annotation: ''{0}''";
	private static final String DATASOURCE_NOT_FOUND = "Datasource ''{0}'' not fount";
	private static final String DATASOURCE_URL_REQUIRED = "Datasource ''{0}.url'' must be required in the '@SQL' annotation: ''{1}''";
	private static final String DATASOURCE_DRIVER_REQUIRED = "Datasource ''{0}.driver'' must be required in the '@SQL' annotation: ''{1}''";
	private static final String DATASOURCE_USERNAME_REQUIRED = "Datasource ''{0}.username'' must be required in the '@SQL' annotation: ''{1}''";
	private static final String DATASOURCE_PASSWORD_REQUIRED = "Datasource ''{0}.password'' must be required in the '@SQL' annotation: ''{1}''";
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

		AnnotationLoader.getSqlPaths(suiteClazz).entrySet()
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
					
					AnnotationLoader.getSqlPaths(suiteClazz).entrySet()
						.forEach(datasource -> {
							validateName(scriptClazz, datasource.getKey());
							validateDatasource(scriptClazz, datasource.getKey());
						});
				});

		chain(suiteClazz);
	}
	
	private void validateName(Class<?> clazz, String datasourceName) {
		if (StringUtil.isBlank(datasourceName)) {
			throw new IntegrityException(DATASOURCE_NAME_REQUIRED, getName(clazz));
		}
	}

	private void validateDatasource(Class<?> clazz, String datasourceName) {

		var configuration = Configuration.getInstance(clazz);
		var datasource = configuration.getDatasource(datasourceName);
		if (Objects.isNull(datasource)) {
			throw new IntegrityException(DATASOURCE_NOT_FOUND, datasourceName);
		}

		if (StringUtil.isBlank(datasource.getUrl())) {
			throw new IntegrityException(DATASOURCE_URL_REQUIRED, datasourceName, getName(clazz));
		}

		if (StringUtil.isBlank(datasource.getDriver())) {
			throw new IntegrityException(DATASOURCE_DRIVER_REQUIRED, datasourceName, getName(clazz));
		}

		if (StringUtil.isBlank(datasource.getUsername())) {
			throw new IntegrityException(DATASOURCE_USERNAME_REQUIRED, datasourceName, getName(clazz));
		}

		if (StringUtil.isBlank(datasource.getPassword())) {
			throw new IntegrityException(DATASOURCE_PASSWORD_REQUIRED, datasourceName, getName(clazz));
		}

		try {
			
			SqlUtil.validateConnection(
					datasource.getUrl(), 
					datasource.getUsername(), 
					datasource.getPassword(), 
					datasource.getDriver(),
					datasource.getSchema());

		} catch (Exception ex) {
			throw new IntegrityException(DATASOURCE_VALIDATE_CONECTION, datasourceName, ex.getMessage());
		}
	}
	
	private String getName(Class<?> scritpClazz) {
		return scritpClazz.getName();
	}

}