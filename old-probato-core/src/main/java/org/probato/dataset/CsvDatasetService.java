package org.probato.dataset;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.probato.api.Dataset;
import org.probato.exception.IntegrityException;
import org.probato.model.Content;
import org.probato.model.Datamodel;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.opencsv.bean.MappingStrategy;

public class CsvDatasetService implements DatasetService {
	
	private static final String MSG_LOAD_ERROR = "Load file ''{0}'' error: {1}";
	private static final String MSG_MUST_DEFAULT_CONSTRUCTOR = "Class must have default constructor: ''{0}''";

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
		for (var path: dataset.value()) {
			try (FileReader reader = readFile(path)) {
				models.addAll(getObjectMapperBuilder(reader, clazz).parse());
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
	
	@Override
	public Content getContent(Dataset dataset, int index) {
		if (index >= 0) {
			var headers = getContent(dataset).get(0);
			var data = getContent(dataset).get(index);
			return new Content(headers, data);
		}
		return newInstance(Content.class);
	}
	
	@SuppressWarnings("resource")
	public List<String[]> getContent(Dataset dataset) {
		var models = new ArrayList<String[]>();
		for (var path: dataset.value()) {
			try (FileReader reader = readFile(path)) {
				models.addAll(new CSVReader(reader).readAll());
			} catch (Exception ex) {
				throw new IntegrityException(MSG_LOAD_ERROR, dataset.value(), ex.getMessage());
			}
		}
		return models;
	}

	private FileReader readFile(String path) throws FileNotFoundException, URISyntaxException {
		return new FileReader(getPath(path).toString());
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
	
	private <T> T newInstance(Class<T> clazz) {
		try {
			return clazz.getConstructor().newInstance();
		} catch (IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException e) {
			throw new IntegrityException(MSG_MUST_DEFAULT_CONSTRUCTOR, clazz.getName());
		}
	}

}