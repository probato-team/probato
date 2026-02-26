package org.probato.service;

import java.util.List;
import java.util.Objects;
import java.util.ServiceLoader;
import java.util.ServiceLoader.Provider;
import java.util.stream.Collectors;

import org.probato.api.Dataset;
import org.probato.dataset.DatasetProvider;
import org.probato.model.Content;
import org.probato.model.Datamodel;

public class DatasetService {

	private List<DatasetProvider> providers;

	private DatasetService() {
		load();
	}

	public int countEntries(Dataset dataset) {
		return get(dataset).countEntries(dataset);
	}

	public <T> T getDatamodel(Dataset dataset, Class<T> clazz, int index) {
		return get(dataset).getDatamodel(dataset, clazz, index);
	}

	public List<Datamodel> getDatamodels(Dataset dataset) {
		return get(dataset).getDatamodels(dataset);
	}

	public <T> List<T> getDatamodels(Dataset dataset, Class<T> clazz) {
		return get(dataset).getDatamodels(dataset, clazz);
	}

	public Content getContent(Dataset dataset, int index) {
		return get(dataset).getContent(dataset, index);
	}

	public static DatasetService get() {
		return new DatasetService();
	}

	private void load() {
		if (Objects.isNull(providers)) {
			providers = ServiceLoader.load(DatasetProvider.class)
					.stream()
					.map(Provider::get)
					.collect(Collectors.toList());
		}
	}

	private DatasetProvider get(Dataset dataset) {
		return providers.stream()
				.filter(provider -> provider.accepted(dataset))
				.findFirst()
				.orElseGet(DefaultProvider::new);
	}

}