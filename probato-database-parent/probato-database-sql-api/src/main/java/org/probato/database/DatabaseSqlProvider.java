package org.probato.database;

import org.probato.exception.IntegrityException;
import org.probato.loader.ConfigurationContext;
import org.probato.loader.SqlLoader;
import org.probato.model.Datasource;
import org.probato.utils.SqlUtils;

public class DatabaseSqlProvider implements SqlProvider {

	private static final String SQL_COMMAND_ERROR_MSG = "An error occurred while executing SQL command: ''{0}'' {1}";

	@Override
	public void run(Class<?> clazz) {
		var configuration = ConfigurationContext.get(clazz);
		SqlLoader.getSqlPaths(clazz).entrySet()
			.forEach(item -> {
				var datasource = configuration.getDatasource(item.getKey());
				item.getValue()
					.stream()
					.forEach(slqFilepath -> executeSqlCommand(datasource, slqFilepath));
			});
	}

	private void executeSqlCommand(Datasource datasource, String slqFilepath) {
		try {

			var  commands = SqlUtils.getQueries(slqFilepath);
			SqlUtils.executeQueries(
					datasource.getUrl(),
					datasource.getUsername(),
					datasource.getPassword(),
					datasource.getDriver(),
					datasource.getSchema(),
					commands);

		} catch (Exception ex) {
			throw new IntegrityException(SQL_COMMAND_ERROR_MSG, slqFilepath, ex.getMessage());
		}
	}

}