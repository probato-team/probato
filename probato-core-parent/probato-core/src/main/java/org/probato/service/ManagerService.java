package org.probato.service;

import java.util.Objects;
import java.util.Optional;
import java.util.ServiceLoader;
import java.util.ServiceLoader.Provider;

import org.probato.exception.ExecutionException;
import org.probato.loader.ConfigurationContext;
import org.probato.manager.ManagerProvider;
import org.probato.model.Configuration;
import org.probato.model.Directory;
import org.probato.model.Execution;
import org.probato.model.Manager;
import org.probato.model.Target;

public class ManagerService {

	private static final String ERROR_DEFAULT_MSG = "An error occurred while executing: {0}";

	private Configuration configuration;
	private ManagerProvider provider;

	private ManagerService() {
		this.configuration = ConfigurationContext.get();
		load();
	}

	public static ManagerService get() {
		return new ManagerService();
	}

	public void managerHealthCheck() {
		try {

			provider.managerHealthCheck(getManager(), getTarget());

		} catch (Exception ex) {
			throw new ExecutionException(ERROR_DEFAULT_MSG, ex.getMessage());
		}
	}

	public void loadIncrementProject() {
		try {

			provider.loadIncrementProject(getManager());
			configuration.getExecution().setIncrement(provider.getIncrement());
			configuration.getExecution().getTarget().setProjectId(provider.getProject());

		} catch (Exception ex) {
			throw new ExecutionException(ERROR_DEFAULT_MSG, ex.getMessage());
		}
	}

	public void sendExecutionData() {
		try {

			provider.sendExecutionData(getManager(), getTarget(), getDirectory());

		} catch (Exception ex) {
			throw new ExecutionException(ERROR_DEFAULT_MSG, ex.getMessage());
		}
	}

	private void load() {
		if (Objects.isNull(provider)) {
			provider = ServiceLoader.load(ManagerProvider.class)
					.stream()
					.map(Provider::get)
					.findFirst()
					.orElse(provider);
		}
	}

	private Manager getManager() {
		return Optional.ofNullable(configuration)
				.map(Configuration::getExecution)
				.map(Execution::getManager)
				.orElse(null);
	}

	private Directory getDirectory() {
		return Optional.ofNullable(configuration)
				.map(Configuration::getExecution)
				.map(Execution::getDirectory)
				.orElse(null);
	}

	private Target getTarget() {
		return Optional.ofNullable(configuration)
				.map(Configuration::getExecution)
				.map(Execution::getTarget)
				.orElse(null);
	}

}