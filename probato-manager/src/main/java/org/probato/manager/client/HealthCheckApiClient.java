package org.probato.manager.client;

import java.net.URI;
import java.net.http.HttpRequest;
import java.util.Objects;

import org.probato.exception.IntegrationException;
import org.probato.manager.client.dto.HealthCheckResponse;
import org.probato.model.Manager;

public class HealthCheckApiClient extends AbstractApiClient {

	private static final String HEALTHCHECK_ENDPOINT = "{0}/health";
	private static final String HEALTHCHECK_UP_RESPONSE = "UP";
	private static final String HEALTHCHECK_NO_UP_MSG = "Web application is currently unavailable";

	public void execute(Manager manager) {
		try {

			if (!isSubmit(manager)) return;

			var request = HttpRequest.newBuilder()
					.GET()
					.uri(URI.create(buildUrl(HEALTHCHECK_ENDPOINT, getBaseUrl(manager))))
					.build();

			var response = send(request);
			var data = converterResponse(response, HealthCheckResponse.class);
			if (!isUP(data)) {
				throw new IntegrationException(HEALTHCHECK_NO_UP_MSG);
			}

		} catch (Exception ex) { // NOSONAR
			throw new IntegrationException(ERRO_INVOKE_MSG, ex.getMessage());
		}
	}

	private static boolean isUP(HealthCheckResponse data) {
		return Objects.nonNull(data) && HEALTHCHECK_UP_RESPONSE.equals(data.getStatus());
	}

}