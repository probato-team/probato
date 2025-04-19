package org.probato.service;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.probato.core.loader.Configuration;
import org.probato.entity.type.ExecutionPhase;
import org.probato.exception.IntegrationException;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;

@TestInstance(Lifecycle.PER_CLASS)
@DisplayName("Test -> HealthCheckApiService")
class HealthCheckApiServiceTest {

	private WireMockServer wireMockServer;

	@BeforeAll
	void init() {
		wireMockServer = new WireMockServer(options().port(9999));
		wireMockServer.start();
	}

	@BeforeEach
	void setup() {

		Configuration.getInstance(getClass());

		WireMock.configureFor("localhost", wireMockServer.port());

        stubFor(get(urlEqualTo("/health"))
                .willReturn(aResponse()
                        .withBody("{\"status\":\"UP\"}")));
	}

	@Test
	@DisplayName("Should integration successfully")
	void shouldIntegrationSuccessfully() {

		IntegrationService.getInstance()
				.stream()
				.filter(service -> service.accepted(ExecutionPhase.BEFORE_EACH))
				.forEach(IntegrationService::run);

		assertTrue(Boolean.TRUE);
	}

	@ParameterizedTest
	@NullSource
	@ValueSource(strings = {
		"{\"status\":\"DOWN\"}",
		"{\"status\":\"OUT_OF_SERVICE\"}",
		"{\"status\":\"UNKNOWN\"}",
	})
	@DisplayName("Should integration failure")
	void shouldIntegrationFailure(String response) {

		stubFor(get(urlEqualTo("/health"))
                .willReturn(aResponse()
                        .withBody(response)));

		var exception = assertThrows(IntegrationException.class,
				() -> IntegrationService.getInstance()
						.stream()
						.filter(apiService -> apiService.accepted(ExecutionPhase.BEFORE_EACH))
						.forEach(IntegrationService::run));

		assertEquals("An error occurred when trying to invoke the web application: Web application is currently unavailable", exception.getMessage());
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