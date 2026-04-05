package org.probato.manager.client;

import java.net.URI;
import java.net.http.HttpRequest;
import java.util.Objects;

import org.probato.exception.IntegrationException;
import org.probato.manager.client.dto.NextIncrementExecutionResponse;
import org.probato.model.Manager;

public class LoadIncrementExecutionApiClient extends AbstractApiClient {

	private static final String GET_INCREMENT_ENDPOINT = "{0}/executions/next-increment";

	public void execute(Manager manager) {
		try {

			if (!isSubmit(manager)) return;

			if (Objects.nonNull(getIncrement())) return;

			var request = HttpRequest.newBuilder()
					.GET()
					.uri(URI.create(buildUrl(GET_INCREMENT_ENDPOINT, getBaseUrl(manager))))
					.header(X_PROJECT_TOKEN, getProjectToken(manager))
					.build();

			var response = send(request);
			var data = converterResponse(response, NextIncrementExecutionResponse.class);

			setIncrement(data.getNextIncrement());
			setProjectId(data.getProjectId());

		} catch (Exception ex) { // NOSONAR
			throw new IntegrationException(ERRO_INVOKE_MSG, ex.getMessage());
		}
	}

}