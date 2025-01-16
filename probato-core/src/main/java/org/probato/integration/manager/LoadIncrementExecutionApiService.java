package org.probato.integration.manager;

import java.net.URI;
import java.net.http.HttpRequest;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.probato.integration.manager.dto.NextIncrementExecutionResponse;
import org.probato.loader.Configuration;
import org.probato.model.type.ExecutionPhase;

public class LoadIncrementExecutionApiService extends AbstractApiService {

	private static final String GET_INCREMENT_ENDPOINT = "{0}/query/executions/next-increment";

	@Override
	public ExecutionPhase getExecutionPhase() {
		return ExecutionPhase.BEFORE_ALL;
	}

	public void execute() throws Exception {

		if (Objects.nonNull(getIncrement())) return;

		var request = HttpRequest.newBuilder()
				.GET()
				.uri(URI.create(buildUrl(GET_INCREMENT_ENDPOINT, getBaseUrl())))
				.header(X_PROJECT_TOKEN, getProjectToken())
				.build();

		var response = send(request);
		var data = converterResponse(response, NextIncrementExecutionResponse.class);

		setIncrement(data.getNextIncrement());
		setProjectId(data.getProjectId());
	}

	private void setIncrement(Long nextIncrement) {
		Optional.ofNullable(Configuration.getInstance().getExecution())
				.ifPresent(execution -> execution.setIncrement(nextIncrement));
	}

	private void setProjectId(UUID projectId) {
		Optional.ofNullable(Configuration.getInstance().getExecution())
				.ifPresent(execution -> execution.getTarget().setProjectId(projectId));
	}

}