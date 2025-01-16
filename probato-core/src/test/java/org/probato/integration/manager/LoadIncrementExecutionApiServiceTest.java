package org.probato.integration.manager;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.UUID;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.probato.exception.IntegrationException;
import org.probato.integration.ExternalService;
import org.probato.loader.Configuration;
import org.probato.model.type.ExecutionPhase;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;

@TestInstance(Lifecycle.PER_CLASS)
@DisplayName("Test -> LoadIncrementExecutionApiService")
class LoadIncrementExecutionApiServiceTest {

	private WireMockServer wireMockServer;

	@BeforeAll
	void init() {
		wireMockServer = new WireMockServer(options().port(9999));
		wireMockServer.start();
	}
	
	@BeforeEach
	void setup() {

		Configuration.getInstance(getClass());

		var projectId = "9f12de88-56ba-45de-8a69-d3038011b357";

		WireMock.configureFor("localhost", wireMockServer.port());

        stubFor(get(urlEqualTo("/query/executions/next-increment"))
        		.willReturn(aResponse().withStatus(201)
        				.withBody("{\"nextIncrement\":1, \"projectId\": \"" + projectId + "\"}")));
	}
	
	@Test
	@DisplayName("Should integration successfully")
	void shouldIntegrationSuccessfully() {

		var configuration = Configuration.getInstance();

		ExternalService.getInstance()
				.stream()
				.filter(service -> service.accepted(ExecutionPhase.BEFORE_ALL))
				.forEach(ExternalService::run);

		assertEquals(UUID.fromString("9f12de88-56ba-45de-8a69-d3038011b357"), configuration.getExecution().getTarget().getProjectId());
		assertEquals(1L, configuration.getExecution().getIncrement());
	}

	@Test
	@DisplayName("Should integration increment exists")
	void shouldIntegrationExists() {
		
		var increment = 1L;
		var projectId = UUID.fromString("9f12de88-56ba-45de-8a69-d3038011b357");

		var configuration = Configuration.getInstance();
		configuration.getExecution().getTarget().setProjectId(projectId);
		configuration.getExecution().setIncrement(increment);
		
		ExternalService.getInstance()
				.stream()
				.filter(service -> service.accepted(ExecutionPhase.BEFORE_ALL))
				.forEach(ExternalService::run);
		
		assertEquals(projectId, configuration.getExecution().getTarget().getProjectId());
		assertEquals(increment, configuration.getExecution().getIncrement());
	}

	@Test
	@DisplayName("Should integration failure")
	void shouldIntegrationFailure() {

		stubFor(get(urlEqualTo("/query/executions/next-increment"))
        		.willReturn(aResponse()
        				.withStatus(400)));

		var exception = assertThrows(IntegrationException.class, 
				() -> ExternalService.getInstance()
						.stream()
						.filter(apiService -> apiService.accepted(ExecutionPhase.BEFORE_ALL))
						.forEach(ExternalService::run));

		assertEquals("An error occurred when trying to invoke the web application: Unable to access application: 400 - ", exception.getMessage());
	}

	@AfterEach
	void clear() {
		wireMockServer.resetAll();
		Configuration.clear();
	}

	@AfterAll
	void destroy() {
		wireMockServer.stop();
	}

}