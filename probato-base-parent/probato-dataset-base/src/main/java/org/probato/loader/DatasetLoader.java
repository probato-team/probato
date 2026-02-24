package org.probato.loader;

import java.util.Optional;

import org.probato.api.Dataset;

public class DatasetLoader {

	private DatasetLoader() {}

	public static boolean hasDataset(Class<?> clazz) {
		return clazz.isAnnotationPresent(Dataset.class);
	}

	public static Optional<Dataset> getDataset(Class<?> clazz) {
		return Optional.ofNullable(clazz.getAnnotation(Dataset.class));
	}

}