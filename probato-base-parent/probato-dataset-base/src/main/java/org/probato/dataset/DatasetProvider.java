package org.probato.dataset;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.probato.api.Dataset;
import org.probato.exception.IntegrityException;
import org.probato.model.Content;
import org.probato.model.Datamodel;

/**
 * Strategy interface responsible for providing access to {@link Datamodel}
 * instances from a dataset source.
 *
 * <p>
 * Implementations of this interface are responsible for interpreting dataset
 * resources (e.g., JSON, YAML, XML, etc.) and converting them into domain
 * objects usable during test execution.
 * </p>
 *
 * <p>
 * Each implementation must declare whether it supports a given dataset source
 * through the {@link #accepted(String)} method.
 * </p>
 *
 * <p>
 * There must be at most one {@code DatasetProvider} implementation per dataset
 * type.
 * </p>
 *
 * @author Felipe Rudolfe
 */
public interface DatasetProvider {

	String MSG_MUST_DEFAULT_CONSTRUCTOR = "Class must have default constructor: ''{0}''";

	/**
	 * Checks whether this provider supports the given dataset source.
	 *
	 * <p>
	 * Typically used to determine compatibility based on file name, extension, or
	 * any other identifier.
	 * </p>
	 *
	 * @param path dataset source path (e.g., file location)
	 * @return {@code true} if this provider can handle the dataset; {@code false}
	 *         otherwise
	 */
	boolean accepted(Dataset dataset);

	/**
	 * Counts the number of entries available in the dataset source.
	 *
	 * <p>
	 * This is typically used for indexed access to datamodels.
	 * </p>
	 *
	 * @param path dataset source path
	 * @return number of entries found in the dataset
	 */
	int countEntries(Dataset dataset);

	/**
	 * Retrieves all datamodel entries from the dataset source and maps them to the
	 * specified target class.
	 *
	 * @param path  dataset source path
	 * @param clazz target class used for mapping
	 * @param <T>   mapped datamodel type
	 * @return list of mapped datamodel instances
	 */
	<T> List<T> getDatamodels(Dataset dataset, Class<T> clazz);

	/**
	 * Retrieves all datamodel entries from the dataset source.
	 *
	 * @param path dataset source path
	 * @return list of {@link Datamodel} instances
	 */
	List<Datamodel> getDatamodels(Dataset dataset);

	/**
	 * Retrieves a specific datamodel entry by index and maps it to the specified
	 * target class.
	 *
	 * @param path  dataset source path
	 * @param clazz target class used for mapping
	 * @param index entry position
	 * @param <T>   mapped datamodel type
	 * @return mapped datamodel instance
	 */
	<T> T getDatamodel(Dataset dataset, Class<T> clazz, int index);

	/**
	 * Retrieves the raw {@link Content} associated with a dataset entry.
	 *
	 * @param path  dataset source path
	 * @param index entry position
	 * @return content associated with the specified index
	 */
	Content getContent(Dataset dataset, int index);

	/**
	 * Retrieves the raw content entries from the dataset source.
	 *
	 * <p>
	 * Each entry is represented as an array of {@link String}, typically
	 * corresponding to structured values such as rows in tabular formats (e.g.,
	 * CSV, spreadsheet-like data, etc.).
	 * </p>
	 *
	 * @param path dataset source path
	 * @return list of content entries extracted from the dataset
	 */
	List<Content> getContent(Dataset dataset);

	public default <T> T newInstance(Class<T> clazz) {
		try {
			return clazz.getConstructor().newInstance();
		} catch (IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException
				| InstantiationException | IllegalAccessException e) {
			throw new IntegrityException(MSG_MUST_DEFAULT_CONSTRUCTOR, clazz.getName());
		}
	}

}