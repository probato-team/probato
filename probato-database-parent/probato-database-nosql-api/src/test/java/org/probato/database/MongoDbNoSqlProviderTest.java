package org.probato.database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.util.stream.Collectors;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.probato.test.suite.UC14_SuiteWithMongoNoSQL;
import org.probato.test.support.DockerSupport;
import org.probato.type.DatasourceType;
import org.testcontainers.containers.MongoDBContainer;

import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import com.mongodb.client.MongoClients;

@DisplayName("UT - MongoDbNoSqlProvider")
class MongoDbNoSqlProviderTest {

	private static final String DATABASE = "testdb";

	private static MongoDBContainer mongo;

	@SuppressWarnings("resource")
	@BeforeAll
	static void setup() {

		assumeTrue(
				DockerSupport.isDockerAvailable(),
				"Docker not available - skipping Testcontainers tests");

		mongo = new MongoDBContainer("mongo:6.0")
				.withExposedPorts(27017)
				.withCreateContainerCmdModifier(cmd -> cmd.withHostConfig(cmd.getHostConfig()
						.withPortBindings(new PortBinding(
								Ports.Binding.bindPort(27017),
								new ExposedPort(27017)))));

		mongo.start();

		try (var client = MongoClients.create(mongo.getConnectionString())) {
			client.getDatabase(DATABASE).listCollectionNames().first();
		}
	}

	@AfterAll
	static void teardown() {
		if (mongo != null) {
			mongo.stop();
		}
	}

	@Test
	@DisplayName("Should execute validator successfully")
	void shouldExecuteValidadorSuccessfully() {

		var providers = NoSqlProvider.getInstance(DatasourceType.MONGODB)
				.stream()
				.filter(NoSqlProvider.class::isInstance)
				.collect(Collectors.toList());

		providers.forEach(validator -> validator.run(UC14_SuiteWithMongoNoSQL.class));

		assertEquals(1, providers.size());
	}

}