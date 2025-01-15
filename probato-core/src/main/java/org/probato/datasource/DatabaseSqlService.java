package org.probato.datasource;

import org.probato.exception.ImpeditiveException;
import org.probato.loader.AnnotationLoader;
import org.probato.loader.Configuration;
import org.probato.model.Datasource;
import org.probato.util.SqlUtil;

public class DatabaseSqlService implements DatasourceService {
	
	private static final String SQL_COMMAND_ERROR_MSG = "An error occurred while executing SQL command:''{0}'' \n{1}";
	
	@Override
	public void run(Class<?> clazz) {
		var configuration = Configuration.getInstance(clazz);
		AnnotationLoader.getSqlPaths(clazz).entrySet()
			.forEach(item -> {
				var datasource = configuration.getDatasource(item.getKey());
				item.getValue()
					.stream()
					.forEach(slqFilepath -> executeSqlCommand(datasource, slqFilepath));
			});
	}
	
	private void executeSqlCommand(Datasource datasource, String slqFilepath) {
		try {

			var  commands = SqlUtil.getQueries(slqFilepath);
			SqlUtil.executeQueries(
					datasource.getUrl(), 
					datasource.getUsername(), 
					datasource.getPassword(), 
					datasource.getDriver(),
					datasource.getSchema(),
					commands);

		} catch (Exception ex) {
			throw new ImpeditiveException(SQL_COMMAND_ERROR_MSG, slqFilepath, ex.getMessage());
		}
	}

}