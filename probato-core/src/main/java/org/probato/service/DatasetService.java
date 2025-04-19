package org.probato.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ServiceLoader;
import java.util.ServiceLoader.Provider;

import org.probato.api.Dataset;
import org.probato.entity.model.Content;
import org.probato.entity.model.Datamodel;
import org.probato.exception.IntegrityException;

public interface DatasetService {

	String DATASET_SERVICE_IMPLEMENTATION_NOT_FOUND = "Dataset service implementation not found";
	String MSG_MUST_DEFAULT_CONSTRUCTOR = "Class must have default constructor: ''{0}''";
	
	public int counterLines(Dataset dataset);
	
	public List<Datamodel> getDatamodels(Dataset dataset);

	public <T> List<T> getDatamodels(Dataset dataset, Class<T> clazz);
	
	public <T> T getDatamodel(Dataset dataset, Class<T> clazz, int index);
	
	public Content getContent(Dataset dataset, int index);
	
	static DatasetService getInstance() {
		return ServiceLoader.load(DatasetService.class)
				.stream()
				.map(Provider::get)
				.sorted(Comparator.comparing(serviceClazz -> serviceClazz.getClass().getPackageName().equals(DatasetService.class.getClass().getPackageName()), Comparator.reverseOrder()))
				.findFirst()
				.orElse(newDefaultInstance());
	}
	
	static DatasetService newDefaultInstance() {
		return new DatasetService() {
			
			@Override
			public <T> List<T> getDatamodels(Dataset dataset, Class<T> clazz) {
				return new ArrayList<>();
			}
			
			@Override
			public List<Datamodel> getDatamodels(Dataset dataset) {
				return new ArrayList<>();
			}
			
			@Override
			public <T> T getDatamodel(Dataset dataset, Class<T> clazz, int index) {
				return newInstance(clazz);
			}
			
			@Override
			public Content getContent(Dataset dataset, int index) {
				return newInstance(Content.class);
			}
			
			@Override
			public int counterLines(Dataset dataset) {
				return 0;
			}
			
			private <T> T newInstance(Class<T> clazz) {
				try {
					return clazz.getConstructor().newInstance();
				} catch (IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException e) {
					throw new IntegrityException(MSG_MUST_DEFAULT_CONSTRUCTOR, clazz.getName());
				}
			}
		};
	}
	
}