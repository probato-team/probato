package org.probato.integration.manager;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
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
import org.probato.core.loader.Configuration;
import org.probato.entity.type.ExecutionPhase;
import org.probato.integration.ExternalService;
import org.probato.util.FileUtil;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;

@TestInstance(Lifecycle.PER_CLASS)
@DisplayName("Test -> DeleteExecutionDataFileService")
class DeleteExecutionDataFileServiceTest {
	
	private WireMockServer wireMockServer;
	
	@BeforeAll
	void init() {
		wireMockServer = new WireMockServer(options().port(9999));
		wireMockServer.start();
	}
	
	@BeforeEach
	void setup() throws IOException {
		
		var configuration = Configuration.getInstance(getClass());
		
		var projectId = "9f12de88-56ba-45de-8a69-d3038011b357";
		var tempDir = "C:/temp/" + projectId + "/1.0.0";
		
		configuration.getExecution().getTarget().setProjectId(UUID.fromString("9f12de88-56ba-45de-8a69-d3038011b357"));
		configuration.getExecution().setIncrement(1L);
		
		var files =FileUtil.loadFiles(tempDir);
		for (File file : files) {
			FileUtil.delete(file);
		}
		
		FileUtil.createTempDir(tempDir);
		FileUtil.save(tempDir, "000-UC01.json", "{}");
		FileUtil.save(tempDir, "001-UC01TC01.json", "{}");
		FileUtil.save(tempDir, "002-e5543501-ffcd-43e4-948b-9dc8576d5c80.json", "{}");
		
		WireMock.configureFor("localhost", wireMockServer.port());
	}

	@Test
	@DisplayName("Should integration successfully")
	void shouldIntegrationSuccessfully() {
		
		var configuration = Configuration.getInstance();
		
		ExternalService.getInstance()
				.stream()
				.filter(service -> service.accepted(ExecutionPhase.AFTER_ALL))
				.forEach(ExternalService::run);

		assertEquals(UUID.fromString("9f12de88-56ba-45de-8a69-d3038011b357"), configuration.getExecution().getTarget().getProjectId());
		assertEquals(1L, configuration.getExecution().getIncrement());
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