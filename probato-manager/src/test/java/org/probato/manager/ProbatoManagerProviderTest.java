package org.probato.manager;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ServiceLoader;
import java.util.UUID;
import java.util.ServiceLoader.Provider;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.probato.model.Directory;
import org.probato.model.Manager;
import org.probato.model.Target;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;

@TestInstance(Lifecycle.PER_CLASS)
@DisplayName("UT - ProbatoManagerProvider")
class ProbatoManagerProviderTest {

	private WireMockServer wireMockServer;
	private ManagerProvider managerProvider;

	@BeforeAll
	void init() {
		wireMockServer = new WireMockServer(options().port(9999));
		wireMockServer.start();
		managerProvider = ServiceLoader.load(ManagerProvider.class)
				.stream()
				.map(Provider::get)
				.findFirst()
				.orElseThrow();
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
	@DisplayName("Should healthCheck successfully")
	void shouldHealthCheckSuccessfully() {

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

		managerProvider.managerHealthCheck(manager, target);

		assertTrue(Boolean.TRUE);
	}

	@Test
	@DisplayName("Should load increment execution successfully")
	void shouldLoadIncrementExecutionSuccessfully() {

		var manager = Manager.builder()
				.submit(Boolean.TRUE)
				.token("ThisIsAToken")
				.url("http://localhost:" + wireMockServer.port())
				.build();

		stubFor(get(urlEqualTo("/executions/next-increment"))
				.willReturn(aResponse()
						.withBody("{\"status\":\"UP\"}")));

		managerProvider.loadIncrementProject(manager);

		assertTrue(Boolean.TRUE);
	}

	@Test
	@DisplayName("Should submit execution data successfully")
	void shouldSubmitExecutionDataSuccessfully() {

		var manager = Manager.builder()
				.submit(Boolean.TRUE)
				.token("ThisIsAToken")
				.url("http://localhost:" + wireMockServer.port())
				.build();

		var directory = new Directory();

		var target = Target.builder()
				.projectId(UUID.randomUUID())
				.url("http:probato.org")
				.version("1.0.0")
				.build();

		managerProvider.sendExecutionData(manager, target, directory);

		assertTrue(Boolean.TRUE);
	}

}