package org.probato.integration;

import java.util.Comparator;
import java.util.List;
import java.util.ServiceLoader;
import java.util.ServiceLoader.Provider;
import java.util.stream.Collectors;

import org.probato.entity.type.ExecutionPhase;

public interface ExternalService {
	
	void run();
	
	ExecutionPhase getExecutionPhase();
	
	default boolean accepted(ExecutionPhase type) {
		return getExecutionPhase().equals(type);
	}

	public static List<ExternalService> getInstance() {
		return ServiceLoader
				.load(ExternalService.class)
				.stream()
				.sorted(Comparator.comparing(service -> service.getClass().getPackageName().equals(ExternalService.class.getClass().getPackageName()), Comparator.reverseOrder()))
				.map(Provider::get)
				.collect(Collectors.toList());
	}

}