package org.probato.manager.client;

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
import org.probato.model.Directory;
import org.probato.model.Manager;
import org.probato.model.Target;
import org.probato.utils.ConverterUtils;

public abstract class AbstractApiClient {

	protected static final String API_INVOKE_MSG = "Unable to access application: {0} - {1}";
	protected static final String ERRO_INVOKE_MSG = "An error occurred when trying to invoke the web application: {0}";
	protected static final String X_PROJECT_TOKEN = "X-Project-Token";

	private static Long increment;
	private static Target target;
	private static Directory directory;

	protected String getBaseUrl(Manager manager) {
		return Optional.ofNullable(manager)
				.map(Manager::getUrl)
				.orElse(null);
	}

	protected boolean isSubmit(Manager manager) {
		return Optional.ofNullable(manager)
				.map(Manager::isSubmit)
				.orElse(Boolean.FALSE);
	}

	protected String getProjectToken(Manager manager) {
		return Optional.ofNullable(manager)
				.map(Manager::getToken)
				.orElse(null);
	}

	public Long getIncrement() {
		return increment;
	}

	protected void setIncrement(Long nextIncrement) {
		AbstractApiClient.increment = nextIncrement;
	}

	public UUID getProjectId() {
		return Optional.ofNullable(target)
				.map(Target::getProjectId)
				.orElse(null);
	}

	protected void setProjectId(UUID projectId) {
		Optional.ofNullable(target)
				.ifPresent(item -> item.setProjectId(projectId));
	}

	public void setTarget(Target target) {
		AbstractApiClient.target = target;
	}

	protected String getProjectVersion() {
		return Optional.ofNullable(target)
				.map(Target::getVersion)
				.orElse(null);
	}

	protected String getTempDir() {
		return Optional.ofNullable(directory)
				.map(Directory::getTemp)
				.orElse(null);
	}

	public void setDirectory(Directory directory) {
		AbstractApiClient.directory = directory;
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
		return ConverterUtils.toObject(response.body(), clazz);
	}

}