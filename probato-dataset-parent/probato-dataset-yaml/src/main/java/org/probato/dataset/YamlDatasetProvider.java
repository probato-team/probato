package org.probato.dataset;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.probato.api.Dataset;
import org.probato.exception.IntegrityException;
import org.probato.model.Content;
import org.probato.model.Datamodel;
import org.probato.utils.StringUtils;
import org.yaml.snakeyaml.Yaml;

public class YamlDatasetProvider implements DatasetProvider {

	private static final String YML_EXTENSION = ".yml";
	private static final String YAML_EXTENSION = ".yaml";
	private static final String MSG_LOAD_ERROR = "Load file ''{0}'' error: {1}";

	@Override
	public boolean accepted(Dataset dataset) {
		return Objects.nonNull(dataset)
				&& (dataset.value().toLowerCase().endsWith(YML_EXTENSION) || dataset.value().toLowerCase().endsWith(YAML_EXTENSION));
	}

	@Override
	public int countEntries(Dataset dataset) {
		return getDatamodels(dataset).size();
	}

	@Override
	public List<Datamodel> getDatamodels(Dataset dataset) {
		return getDatamodels(dataset, Datamodel.class);
	}

	@Override
	public <T> T getDatamodel(Dataset dataset, Class<T> clazz, int index) {
		if (index >= 0) {
			return getDatamodels(dataset, clazz).get(index);
		}
		return newInstance(clazz);
	}

	@Override
	public <T> List<T> getDatamodels(Dataset dataset, Class<T> clazz) {

		validate(dataset);

		var models = new ArrayList<T>();
		try (var inputStream = getResource(dataset)) {

			Optional.ofNullable(inputStream)
				.orElseThrow(() -> new IntegrityException("File not found"));

			models.addAll(getEntries(inputStream, clazz));

		} catch (Exception ex) {
			throw new IntegrityException(MSG_LOAD_ERROR, dataset.value(), ex.getMessage());
		}

		return models;
	}

	@Override
	public Content getContent(Dataset dataset, int index) {
		if (index >= 0) {
			return getContent(dataset).get(index);
		}
		return newInstance(Content.class);
	}

	@Override
	public List<Content> getContent(Dataset dataset) {

		validate(dataset);

		var models = new ArrayList<Content>();
		try (var inputStream = getResource(dataset)) {

			Optional.ofNullable(inputStream)
				.orElseThrow(() -> new IntegrityException("File not found"));

			models.addAll(getEntries(inputStream, Content.class));

		} catch (Exception ex) {
			throw new IntegrityException(MSG_LOAD_ERROR, dataset.value(), ex.getMessage());
		}

		return models;
	}

	private void validate(Dataset dataset) {
		if (Objects.isNull(dataset) || StringUtils.isBlank(dataset.value())) {
			throw new IntegrityException("Dataset path not defined");
		}
	}

	private InputStream getResource(Dataset dataset) {
		return Thread.currentThread().getContextClassLoader().getResourceAsStream(dataset.value());
	}

	private <T> List<T> getEntries(InputStream inputStream, Class<T> clazz) {
		try {

			var yaml = new Yaml();
			var loaded = yaml.load(inputStream);

			if (Objects.isNull(loaded)) {
				return List.of();
			}

			List<?> entries;

			if (loaded instanceof List<?>) {
				entries = (List<?>) loaded;
			} else if (loaded instanceof Map<?, ?>) {
				entries = extractFirstList((Map<?, ?>) loaded);
			} else {
				entries = List.of(loaded);
			}

			return mapList(entries, clazz);

		} catch (Exception ex) {
			throw new IntegrityException("YAML parsing error: {0}", ex.getMessage());
		}
	}

	private List<?> extractFirstList(Map<?, ?> map) {
		for (var value : map.values()) {
			if (value instanceof List<?>) {
				return (List<?>) value;
			}
		}
		return List.of();
	}

	private <T> List<T> mapList(List<?> entries, Class<T> clazz) {
		var result = new ArrayList<T>();
		for (Object entry : entries) {
			result.add(map(entry, clazz));
		}
		return result;
	}

	private <T> T map(Object source, Class<T> clazz) {
		try {

			var instance = clazz.getDeclaredConstructor().newInstance();
			if (!(source instanceof Map<?, ?>)) {
				return instance;
			}

			Class<?> current = clazz;
			while (Objects.nonNull(current)) {

				for (var field : current.getDeclaredFields()) {
					field.setAccessible(true);
					var map = (Map<?, ?>) source;
					var value = map.get(field.getName());
					if (Objects.nonNull(value)) {
						field.set(instance, convert(value, field.getType()));
					}
				}

				current = current.getSuperclass();
			}

			return instance;

		} catch (Exception ex) {
			throw new IntegrityException("Mapping class error {0}: {1}", clazz.getName(), ex.getMessage());
		}
	}

	private Object convert(Object value, Class<?> targetType) {

		if (Objects.isNull(value))
			return null;

		if (targetType.isAssignableFrom(value.getClass())) {
			return value;
		}

		if (value instanceof Map<?, ?> && !isPrimitiveOrWrapper(targetType) && targetType != String.class) {
			return map(( Map<?, ?>) value, targetType);
		}

		if (targetType == String.class) {
			return String.valueOf(value);
		}

		if (targetType == Integer.class || targetType == int.class) {
			return Integer.valueOf(value.toString());
		}

		if (targetType == Long.class || targetType == long.class) {
			return Long.valueOf(value.toString());
		}

		if (targetType == Boolean.class || targetType == boolean.class) {
			return Boolean.valueOf(value.toString());
		}

		if (targetType == Double.class || targetType == double.class) {
			return Double.valueOf(value.toString());
		}

		return value;
	}

	private boolean isPrimitiveOrWrapper(Class<?> type) {
		return type.isPrimitive()
				|| type == Integer.class
				|| type == Long.class
				|| type == Double.class
				|| type == Boolean.class
				|| type == String.class;
	}

}