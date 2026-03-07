package org.probato.database;

import java.util.Comparator;
import java.util.List;
import java.util.ServiceLoader;
import java.util.ServiceLoader.Provider;
import java.util.stream.Collectors;

import org.probato.type.DatasourceType;

/**
 * Contract for executing NoSQL-based operations associated with a given class.
 *
 * <p>
 * Implementations of this interface are responsible for locating and executing
 * NoSQL scripts or statements related to the specified class. This is typically
 * used to prepare, populate, or clean up data sources required during test
 * execution.
 * </p>
 *
 * <p>
 * The provided class usually represents a test, dataset, or domain component
 * whose associated NoSQL resources must be executed.
 * </p>
 */
public interface NoSqlProvider {

	/**
	 * Returns the datasource type supported by this provider.
	 *
	 * @return the supported {@link DatasourceType}
	 */
	DatasourceType getType();

	/**
	 * Executes NoSQL operations associated with the given class.
	 *
	 * <p>
	 * Implementations may use the class as a reference to resolve NoSQL scripts,
	 * annotations, or naming conventions that determine which statements should be
	 * executed.
	 * </p>
	 *
	 * @param clazz the reference class used to resolve and execute NoSQL resources
	 */
	void run(Class<?> clazz);

	public static List<NoSqlProvider> getInstance(DatasourceType type) {
		return ServiceLoader.load(NoSqlProvider.class)
				.stream()
				.map(Provider::get)
				.sorted(Comparator
						.comparing(
								serviceClazz -> serviceClazz.getClass().getPackageName()
										.equals(DatasourceType.class.getClass().getPackageName()),
								Comparator.reverseOrder()))
				.filter(component -> component.getType().equals(type)).collect(Collectors.toList());
	}

}