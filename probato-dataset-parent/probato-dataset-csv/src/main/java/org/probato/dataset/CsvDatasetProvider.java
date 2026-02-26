package org.probato.dataset;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.probato.api.Dataset;
import org.probato.exception.IntegrityException;
import org.probato.model.Content;
import org.probato.model.Datamodel;
import org.probato.utils.StringUtils;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.opencsv.bean.MappingStrategy;

public class CsvDatasetProvider implements DatasetProvider {

	private static final String CSV_EXTENSION = ".csv";
	private static final String MSG_LOAD_ERROR = "Load file ''{0}'' error: {1}";

	@Override
	public boolean accepted(Dataset dataset) {
		return Objects.nonNull(dataset) && dataset.value().toLowerCase().endsWith(CSV_EXTENSION);
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
		try (var reader = readFile(dataset)) {
			models.addAll(getObjectMapperBuilder(reader, clazz).parse());
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
		try (var reader = readFile(dataset); var csv = new CSVReader(reader)) {

			var list = csv.readAll();
			var headers = list.get(0);
			for (int i = 1; i < list.size(); i++) {
				var data = list.get(i);
				models.add(new Content(headers, data));
			}

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

	private FileReader readFile(Dataset dataset) throws FileNotFoundException, URISyntaxException {
		return new FileReader(getPath(dataset.value()).toString());
	}

	private Path getPath(String path) throws URISyntaxException {
		return Paths.get(getURI(path));
	}

	private URI getURI(String path) throws URISyntaxException {
		return getURL(path).toURI();
	}

	private URL getURL(String url) {
		return Optional.ofNullable(ClassLoader.getSystemResource(url))
				.orElseThrow(() -> new IntegrityException("File not found"));
	}

	private <T> CsvToBean<T> getObjectMapperBuilder(FileReader reader, Class<T> clazz) {
		return new CsvToBeanBuilder<T>(reader)
		          .withType(clazz)
		          .withMappingStrategy(getObjectMapperStrategy(clazz))
		          .build();
	}

	private <T> MappingStrategy<T> getObjectMapperStrategy(Class<T> clazz) {
		var strategy = new HeaderColumnNameMappingStrategy<T>();
		strategy.setType(clazz);
		return strategy;
	}

}