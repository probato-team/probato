package org.probato.service;

import java.util.Comparator;
import java.util.List;
import java.util.ServiceLoader;
import java.util.ServiceLoader.Provider;
import java.util.stream.Collectors;

import org.probato.entity.type.ExecutionPhase;

public interface IntegrationService {
	
	void run();
	
	ExecutionPhase getExecutionPhase();
	
	default boolean accepted(ExecutionPhase type) {
		return getExecutionPhase().equals(type);
	}

	public static List<IntegrationService> getInstance() {
		return ServiceLoader
				.load(IntegrationService.class)
				.stream()
				.sorted(Comparator.comparing(service -> service.getClass().getPackageName().equals(IntegrationService.class.getClass().getPackageName()), Comparator.reverseOrder()))
				.map(Provider::get)
				.collect(Collectors.toList());
	}

}