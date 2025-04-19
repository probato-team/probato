package org.probato.service;

import java.util.Comparator;
import java.util.Optional;
import java.util.ServiceLoader;
import java.util.ServiceLoader.Provider;

import org.probato.entity.model.Dimension;

public interface RecordService {

	void start(String outputFile, Dimension dimension);

	void stop();
	
	void screenshot(String outputFile, Dimension dimension);
	
	static Optional<RecordService> getInstance() {
		return ServiceLoader.load(RecordService.class)
				.stream()
				.map(Provider::get)
				.sorted(Comparator.comparing(service -> service.getClass().getPackageName().equals(RecordService.class.getClass().getPackageName()), Comparator.reverseOrder()))
				.findFirst();
	}

}