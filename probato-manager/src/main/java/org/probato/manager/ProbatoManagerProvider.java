package org.probato.manager;

import java.util.UUID;

import org.probato.manager.client.HealthCheckApiClient;
import org.probato.manager.client.LoadIncrementExecutionApiClient;
import org.probato.manager.client.SubmitExecutionDataApiClient;
import org.probato.model.Directory;
import org.probato.model.Manager;
import org.probato.model.Target;

public class ProbatoManagerProvider implements ManagerProvider {

	private HealthCheckApiClient healthCheckApiClient;
	private LoadIncrementExecutionApiClient loadIncrementExecutionApiClient;
	private SubmitExecutionDataApiClient submitExecutionDataApiClient;

	public ProbatoManagerProvider() {
		this.healthCheckApiClient = new HealthCheckApiClient();
		this.loadIncrementExecutionApiClient = new LoadIncrementExecutionApiClient();
		this.submitExecutionDataApiClient = new SubmitExecutionDataApiClient();
	}

	@Override
	public void managerHealthCheck(Manager manager) {
		healthCheckApiClient.execute(manager);
	}

	@Override
	public void loadIncrementProject(Manager manager) {
		loadIncrementExecutionApiClient.execute(manager);
	}

	@Override
	public void sendExecutionData(Manager manager, Target target, Directory directory) {
		submitExecutionDataApiClient.execute(manager, target, directory);
	}

	@Override
	public Long getIncrement() {
		return loadIncrementExecutionApiClient.getIncrement();
	}

	@Override
	public UUID getProject() {
		return loadIncrementExecutionApiClient.getProjectId();
	}

}