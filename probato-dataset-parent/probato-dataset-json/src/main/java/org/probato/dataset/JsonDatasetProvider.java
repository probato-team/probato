package org.probato.dataset;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.probato.api.Dataset;
import org.probato.exception.IntegrityException;
import org.probato.model.Content;
import org.probato.model.Datamodel;
import org.probato.utils.StringUtils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonDatasetProvider implements DatasetProvider {

	private static final String JSON_EXTENSION = ".json";
	private static final String MSG_LOAD_ERROR = "Load file ''{0}'' error: {1}";

	private final ObjectMapper mapper = new ObjectMapper()
			.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

	@Override
	public boolean accepted(Dataset dataset) {
		return Objects.nonNull(dataset) && dataset.value().toLowerCase().endsWith(JSON_EXTENSION);
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

			models.addAll(getModels(inputStream, clazz));
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
		return  newInstance(Content.class);
	}

	@Override
	public List<Content> getContent(Dataset dataset) {

		validate(dataset);

		var models = new ArrayList<Content>();
		try (var inputStream = getResource(dataset)) {

			Optional.ofNullable(inputStream)
				.orElseThrow(() -> new IntegrityException("File not found"));

			models.addAll(getModels(inputStream, Content.class));

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

	private <T> List<T> getModels(InputStream inputStream, Class<T> clazz) throws IOException {
		var type = mapper.getTypeFactory().constructCollectionType(List.class, clazz);
		return mapper.readValue(inputStream, type);
	}

	private InputStream getResource(Dataset dataset) {
		return Thread.currentThread().getContextClassLoader().getResourceAsStream(dataset.value());
	}

}