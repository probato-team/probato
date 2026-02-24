package org.probato.integration.manager;

import java.net.URI;
import java.net.http.HttpRequest;
import java.util.Objects;

import org.probato.exception.IntegrationException;
import org.probato.integration.manager.dto.HealthCheckResponse;
import org.probato.model.type.ExecutionPhase;

public class HealthCheckApiService extends AbstractApiService {

	private static final String HEALTHCHECK_ENDPOINT = "{0}/health";
	private static final String HEALTHCHECK_UP_RESPONSE = "UP";
	private static final String HEALTHCHECK_NO_UP_MSG = "Web application is currently unavailable";

	@Override
	public ExecutionPhase getExecutionPhase() {
		return ExecutionPhase.BEFORE_EACH;
	}

	public void execute() throws Exception {
		
		var request = HttpRequest.newBuilder()
				.GET()
				.uri(URI.create(buildUrl(HEALTHCHECK_ENDPOINT, getBaseUrl())))
				.build();

		var response = send(request);
		var data = converterResponse(response, HealthCheckResponse.class);
		if (!isUP(data)) {
			throw new IntegrationException(HEALTHCHECK_NO_UP_MSG);
		}
	}

	private static boolean isUP(HealthCheckResponse data) {
		return Objects.nonNull(data) && HEALTHCHECK_UP_RESPONSE.equals(data.getStatus());
	}

}