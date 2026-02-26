package org.probato.database;

/**
 * Contract for executing SQL-based operations associated with a given class.
 *
 * <p>
 * Implementations of this interface are responsible for locating and executing
 * SQL scripts or statements related to the specified class. This is typically
 * used to prepare, populate, or clean up data sources required during test
 * execution.
 * </p>
 *
 * <p>
 * The provided class usually represents a test, dataset, or domain component
 * whose associated SQL resources must be executed.
 * </p>
 */
public interface SqlProvider {

	/**
	 * Executes SQL operations associated with the given class.
	 *
	 * <p>
	 * Implementations may use the class as a reference to resolve SQL scripts,
	 * annotations, or naming conventions that determine which statements should be
	 * executed.
	 * </p>
	 *
	 * @param clazz the reference class used to resolve and execute SQL resources
	 */
	void run(Class<?> clazz);

}