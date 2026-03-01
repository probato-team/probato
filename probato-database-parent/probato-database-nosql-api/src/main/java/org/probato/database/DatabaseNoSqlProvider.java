package org.probato.database;

import org.probato.exception.IntegrityException;
import org.probato.loader.ConfigurationContext;
import org.probato.loader.NoSqlLoader;
import org.probato.model.Datasource;
import org.probato.utils.NoSqlUtils;

public class DatabaseNoSqlProvider implements NoSqlProvider {

	private static final String NOSQL_COMMAND_ERROR_MSG = "An error occurred while executing NoSQL command: ''{0}'' {1}";

	@Override
	public void run(Class<?> clazz) {
		var configuration = ConfigurationContext.get(clazz);
		NoSqlLoader.getNoSqlPaths(clazz).entrySet()
			.forEach(item -> {
				var datasource = configuration.getDatasource(item.getKey());
				item.getValue()
					.stream()
					.forEach(slqFilepath -> executeNoSqlCommand(datasource, slqFilepath));
			});
	}

	private void executeNoSqlCommand(Datasource datasource, String slqFilepath) {
		try {

			var  documents = NoSqlUtils.getDocuments(slqFilepath);
			NoSqlUtils.executeDocuments(
					datasource.getUrl(),
					datasource.getDatabase(),
					documents);

		} catch (Exception ex) {
			throw new IntegrityException(NOSQL_COMMAND_ERROR_MSG, slqFilepath, ex.getMessage());
		}
	}

}