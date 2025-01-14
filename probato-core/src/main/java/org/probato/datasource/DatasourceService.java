package org.probato.datasource;

import java.util.Comparator;
import java.util.List;
import java.util.ServiceLoader;
import java.util.ServiceLoader.Provider;
import java.util.stream.Collectors;

import org.probato.dataset.DatasetService;

public interface DatasourceService {

	void run(Class<?> clazz);
	
	static List<DatasourceService> getInstance() {
		return ServiceLoader.load(DatasourceService.class)
				.stream()
				.map(Provider::get)
				.sorted(Comparator.comparing(serviceClazz -> serviceClazz.getClass().getPackageName().equals(DatasetService.class.getClass().getPackageName()), Comparator.reverseOrder()))
				.collect(Collectors.toList());
	}
	
	static boolean hasImplementation() {
		return !ServiceLoader.load(DatasourceService.class)
				.stream()
				.map(Provider::type)
				.collect(Collectors.toList())
				.isEmpty();
	}

}