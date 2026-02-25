package org.probato.service;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.probato.api.Dataset;
import org.probato.exception.IntegrityException;
import org.probato.model.Content;
import org.probato.model.Datamodel;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonDatasetService implements DatasetService {

	private static final String JSON_EXTENSION = ".json";
	private static final String MSG_LOAD_ERROR = "Load file ''{0}'' error: {1}";

	private final ObjectMapper mapper = new ObjectMapper()
			.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

	@Override
	public boolean accepted(String path) {
		return Objects.nonNull(path) && path.toLowerCase().endsWith(JSON_EXTENSION);
	}

	@Override
	public int counterLines(Dataset dataset) {
		return getDatamodels(dataset).size();
	}

	@Override
	public List<Datamodel> getDatamodels(Dataset dataset) {
		return getDatamodels(dataset, Datamodel.class);
	}

	@Override
	public <T> List<T> getDatamodels(Dataset dataset, Class<T> clazz) {
		var models = new ArrayList<T>();
		for (var path : getPaths(dataset)) {
			try (var inputStream = getResource(path)) {

				Optional.ofNullable(inputStream)
					.orElseThrow(() -> new IntegrityException("File not found"));

				models.addAll(getModels(inputStream, clazz));
			} catch (Exception ex) {
				throw new IntegrityException(MSG_LOAD_ERROR, path, ex.getMessage());
			}
		}
		return models;
	}

	@Override
	public <T> T getDatamodel(Dataset dataset, Class<T> clazz, int index) {
		if (index >= 0) {
			return getDatamodels(dataset, clazz).get(index);
		}
		return newInstance(clazz);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Content getContent(Dataset dataset, int index) {
		if (index >= 0) {
			var data = (Map<String, Object>) getContent(dataset).get(index);
			return new Content(data);
		}
		return newInstance(Content.class);
	}

	public List<Object> getContent(Dataset dataset) {
		var models = new ArrayList<Object>();
		for (var path: getPaths(dataset)) {
			try (var inputStream = getResource(path)) {

				Optional.ofNullable(inputStream)
					.orElseThrow(() -> new IntegrityException("File not found"));

				models.addAll(getModels(inputStream, Object.class));

			} catch (Exception ex) {
				throw new IntegrityException(MSG_LOAD_ERROR, path, ex.getMessage());
			}
		}
		return models;
	}

	private String[] getPaths(Dataset dataset) {

		if (dataset == null || dataset.value() == null || dataset.value().length == 0) {
			throw new IntegrityException("Dataset path not defined");
		}

		return dataset.value();
	}

	private <T> List<T> getModels(InputStream inputStream, Class<T> clazz) throws IOException {
		var type = mapper.getTypeFactory().constructCollectionType(List.class, clazz);
		return mapper.readValue(inputStream, type);
	}

	private InputStream getResource(String path) {
		return Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
	}

	private <T> T newInstance(Class<T> clazz) {
		try {
			return clazz.getConstructor().newInstance();
		} catch (IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException e) {
			throw new IntegrityException(MSG_MUST_DEFAULT_CONSTRUCTOR, clazz.getName());
		}
	}

}