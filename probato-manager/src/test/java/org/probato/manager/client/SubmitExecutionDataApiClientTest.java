package org.probato.manager.client;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.put;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
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
import org.probato.model.Directory;
import org.probato.model.Manager;
import org.probato.model.Target;
import org.probato.utils.FileUtils;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;

@TestInstance(Lifecycle.PER_CLASS)
@DisplayName("UT - SubmitExecutionDataApiClient")
class SubmitExecutionDataApiClientTest {

	private WireMockServer wireMockServer;
	private SubmitExecutionDataApiClient client;

	@BeforeAll
	void init() {
		wireMockServer = new WireMockServer(options().port(9999));
		wireMockServer.start();
		client = new SubmitExecutionDataApiClient();
	}

	@BeforeEach
	void setup() throws IOException {

		var projectId = "9f12de88-56ba-45de-8a69-d3038011b357";
		var directory = new Directory();
		var target = Target.builder()
				.projectId(UUID.fromString(projectId))
				.version("1.0.0")
				.url("http://google.com")
				.build();

		WireMock.configureFor("localhost", wireMockServer.port());

		var tempDir = directory.getTemp() + "/" + projectId + "/1.0.0";

		client.setDirectory(directory);
		client.setProjectId(UUID.fromString(projectId));
		client.setTarget(target);

		FileUtils.createTempDir(tempDir);
		FileUtils.save(tempDir, "000-UC01.json", "{}");
		FileUtils.save(tempDir, "001-UC01TC01.json", "{}");
		FileUtils.save(tempDir, "002-e5543501-ffcd-43e4-948b-9dc8576d5c80.json", "{}");

		WireMock.configureFor("localhost", wireMockServer.port());

        stubFor(put(urlEqualTo("/suites")).willReturn(aResponse()));
        stubFor(put(urlEqualTo("/scripts")).willReturn(aResponse()));
        stubFor(post(urlEqualTo("/executions")).willReturn(aResponse()));
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

		var directory = new Directory();

		var target = Target.builder()
				.projectId(UUID.randomUUID())
				.url("http:probato.org")
				.version("1.0.0")
				.build();

		client.execute(manager, target, directory);

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

		var directory = new Directory();

		var target = Target.builder()
				.projectId(UUID.fromString("9f12de88-56ba-45de-8a69-d3038011b357"))
				.url("http://probato.org")
				.version("1.0.0")
				.build();

		stubFor(post(urlEqualTo("/executions")).willReturn(aResponse().withStatus(400)));

		var exception = assertThrows(IntegrationException.class,
				() -> client.execute(manager, target, directory));

		assertEquals("An error occurred when trying to invoke the web application: Unable to access application: 400 - ", exception.getMessage());
	}

}