package org.probato.manager.client;

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
import org.probato.exception.IntegrationException;
import org.probato.model.Manager;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;

@TestInstance(Lifecycle.PER_CLASS)
@DisplayName("UT - LoadIncrementExecutionApiClient")
class LoadIncrementExecutionApiClientTest {

	private WireMockServer wireMockServer;
	private LoadIncrementExecutionApiClient client;

	@BeforeAll
	void init() {
		wireMockServer = new WireMockServer(options().port(9999));
		wireMockServer.start();
		client = new LoadIncrementExecutionApiClient();
	}

	@BeforeEach
	void setup() {

		WireMock.configureFor("localhost", wireMockServer.port());

        stubFor(get(urlEqualTo("/executions/next-increment"))
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

		client.execute(manager);

		assertTrue(Boolean.TRUE);
	}

	@Test
	@DisplayName("Should integration failure")
	void shouldIntegrationFailure() {

		var manager = Manager.builder()
				.submit(Boolean.TRUE)
				.token("ThisIsAToken")
				.url("http://localhost:" + wireMockServer.port())
				.build();

		stubFor(get(urlEqualTo("/executions/next-increment"))
        		.willReturn(aResponse()
        				.withStatus(400)));

		var exception = assertThrows(IntegrationException.class,
				() -> client.execute(manager));

		assertEquals("An error occurred when trying to invoke the web application: Unable to access application: 400 - ", exception.getMessage());
	}

}