package org.probato.manager.client;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;

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
import org.probato.exception.IntegrationException;
import org.probato.model.Manager;
import org.probato.model.Target;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;

@TestInstance(Lifecycle.PER_CLASS)
@DisplayName("UT - HealthCheckApiClient")
class HealthCheckApiClientTest {

	private WireMockServer wireMockServer;
	private HealthCheckApiClient client;

	@BeforeAll
	void init() {
		wireMockServer = new WireMockServer(options().port(9999));
		wireMockServer.start();
		client = new HealthCheckApiClient();
	}

	@BeforeEach
	void setup() {

		WireMock.configureFor("localhost", wireMockServer.port());

        stubFor(get(urlEqualTo("/health"))
                .willReturn(aResponse()
                        .withBody("{\"status\":\"UP\"}")));
	}

	@AfterEach
	void clear() {
		wireMockServer.resetAll();
	}

	@AfterAll
	void destroy() {
		wireMockServer.stop();
	}

	@Test
	@DisplayName("Should integration successfully")
	void shouldIntegrationSuccessfully() {

		var manager = Manager.builder()
				.submit(Boolean.TRUE)
				.token("ThisIsAToken")
				.url("http://localhost:" + wireMockServer.port())
				.build();

		var target = Target.builder()
				.projectId(UUID.randomUUID())
				.version("1.0.0")
				.url("http://google.com")
				.build();

		client.execute(manager, target);

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

		var manager = Manager.builder()
				.submit(Boolean.TRUE)
				.token("ThisIsAToken")
				.url("http://localhost:" + wireMockServer.port())
				.build();

		var target = Target.builder()
				.projectId(UUID.randomUUID())
				.version("1.0.0")
				.url("http://google.com")
				.build();

		stubFor(get(urlEqualTo("/health"))
                .willReturn(aResponse()
                        .withBody(response)));

		var exception = assertThrows(IntegrationException.class,
				() -> client.execute(manager, target));

		assertEquals("An error occurred when trying to invoke the web application: Web application is currently unavailable", exception.getMessage());
	}

}