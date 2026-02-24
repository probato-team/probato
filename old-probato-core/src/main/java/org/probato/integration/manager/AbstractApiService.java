package org.probato.integration.manager;

import static java.net.HttpURLConnection.HTTP_CREATED;
import static java.net.HttpURLConnection.HTTP_OK;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.MessageFormat;
import java.util.Optional;
import java.util.UUID;

import org.probato.exception.IntegrationException;
import org.probato.integration.ExternalService;
import org.probato.loader.Configuration;
import org.probato.model.Execution;
import org.probato.util.ConverterUtil;

public abstract class AbstractApiService implements ExternalService {

	private static final String API_INVOKE_MSG = "Unable to access application: {0} - {1}";
	private static final String ERRO_INVOKE_MSG = "An error occurred when trying to invoke the web application: {0}";
	protected static final String X_PROJECT_TOKEN = "X-Project-Token";
	
	protected abstract void execute() throws Exception;

	@Override
	public void run() {
		try {
			
			if (!isSubmit()) return;
			
			execute();
			
		} catch (Exception ex) { // NOSONAR
			throw new IntegrationException(ERRO_INVOKE_MSG, ex.getMessage());
		}
	}

	protected String getBaseUrl() {
		return Optional.ofNullable(Configuration.getInstance().getExecution())
				.map(execution -> execution.getManager().getUrl())
				.orElse(null);
	}

	protected boolean isSubmit() {
		return Optional.ofNullable(Configuration.getInstance().getExecution())
				.map(execution -> execution.getManager().isSubmit())
				.orElse(Boolean.FALSE);
	}

	protected String getProjectToken() {
		return Optional.ofNullable(Configuration.getInstance().getExecution())
				.map(execution -> execution.getManager().getToken())
				.orElse(null);
	}
	
	protected Long getIncrement() {
		return Optional.ofNullable(Configuration.getInstance().getExecution())
				.map(Execution::getIncrement)
				.orElse(null);
	}
	
	protected UUID getProjectId() {
		return Optional.ofNullable(Configuration.getInstance().getExecution())
				.map(execution -> execution.getTarget().getProjectId())
				.orElse(null);
	}

	protected String getProjectVersion() {
		return Optional.ofNullable(Configuration.getInstance().getExecution())
				.map(execution -> execution.getTarget().getVersion())
				.orElse(null);
	}

	protected String getTempDir() {
		return Optional.ofNullable(Configuration.getInstance().getExecution())
				.map(execution -> execution.getDirectory().getTemp())
				.orElse(null);
	}
	
	protected String buildUrl(String pattern, Object ... params) {
		return MessageFormat.format(pattern, params);
	}

	protected HttpResponse<String> send(HttpRequest request) throws IOException, InterruptedException {
		HttpResponse<String> response = null;
		var client = HttpClient.newHttpClient();
		response = client.send(request, HttpResponse.BodyHandlers.ofString());
		if (HTTP_OK != response.statusCode() && HTTP_CREATED != response.statusCode()) {
			throw new IntegrationException(API_INVOKE_MSG, response.statusCode(), response.body());
		}
		return response;
	}

	protected <T> T converterResponse(HttpResponse<String> response, Class<T> clazz) {
		return ConverterUtil.toObject(response.body(), clazz);
	}

}