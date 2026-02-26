package org.probato.service;

import java.util.ArrayList;
import java.util.List;

import org.probato.api.Dataset;
import org.probato.dataset.DatasetProvider;
import org.probato.model.Content;
import org.probato.model.Datamodel;

public class DefaultProvider implements DatasetProvider {

	@Override
	public boolean accepted(Dataset dataset) {
		return Boolean.TRUE;
	}

	@Override
	public int countEntries(Dataset dataset) {
		return 0;
	}

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
	public List<Content> getContent(Dataset dataset) {
		return new ArrayList<>();
	}

}