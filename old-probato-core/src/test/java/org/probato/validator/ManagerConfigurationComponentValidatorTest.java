package org.probato.validator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.Stream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.probato.exception.IntegrityException;
import org.probato.loader.Configuration;
import org.probato.loader.Configuration.ConfigurationBuilder;
import org.probato.model.Browser;
import org.probato.model.Delay;
import org.probato.model.Dimension;
import org.probato.model.Directory;
import org.probato.model.Execution;
import org.probato.model.Manager;
import org.probato.model.Target;
import org.probato.model.Video;
import org.probato.model.type.BrowserType;
import org.probato.model.type.ComponentValidatorType;
import org.probato.model.type.Quality;
import org.probato.model.type.Screen;
import org.probato.test.suite.UC01_Suite;

@TestInstance(Lifecycle.PER_CLASS)
@DisplayName("Test -> ManagerConfigurationComponentValidator")
class ManagerConfigurationComponentValidatorTest {
	
	@AfterAll
	void destroy() throws Exception {
		
		var config = Configuration.getInstance(UC01_Suite.class);
		var instance = config.getClass().getDeclaredField("instance");
		instance.setAccessible(Boolean.TRUE);
		instance.set(config, null);
	}

	@Test
	@DisplayName("Should execute validator successfully")
	void shouldExecuteValidatorSuccessfully() {

		var validators = ComponentValidator.getInstance(ComponentValidatorType.CONFIGURATION);

		validators.forEach(validator -> validator.execute(UC01_Suite.class));

		assertEquals(2, validators.size());
	}
	
	@ParameterizedTest
	@MethodSource("getInvalidConfigurationData")
	@DisplayName("Should validate configuration data")
	void shouldValidateConfigurationData(Configuration configuration, String message) throws Exception {

		var config = Configuration.getInstance(UC01_Suite.class);
		var instance = config.getClass().getDeclaredField("instance");
		instance.setAccessible(Boolean.TRUE);
		instance.set(config, configuration);

		var validators = ComponentValidator.getInstance(ComponentValidatorType.CONFIGURATION);

		var exception = assertThrows(IntegrityException.class,
				() -> validators.forEach(validator -> validator.execute(UC01_Suite.class)));

		assertEquals(message, exception.getMessage());

		instance.set(configuration, null);
	}

	private static Stream<Arguments> getInvalidConfigurationData() {
		return Stream.of(
				Arguments.of(
						getConfiguration()
							.execution(null)
							.build(),
						"Property 'execution' should be declared in 'configuration.yml' file"),
				Arguments.of(
						getConfiguration()
							.execution(Execution.builder()
								.delay(Delay.builder()
									.actionInterval(1)
									.waitingTimeout(1)
									.build())
								.directory(Directory.builder()
									.temp("/probato/temp")
									.build())
								.target(Target.builder()
									.url("http://localhost:8080")
									.version("V1.0.0")
									.build())
								.manager(null)
								.build())
							.build(),
						"Property 'execution.manager' should be declared in 'configuration.yml' file"),
				Arguments.of(
						getConfiguration()
							.execution(Execution.builder()
								.delay(Delay.builder()
									.actionInterval(1)
									.waitingTimeout(1)
									.build())
								.directory(Directory.builder()
									.temp("/probato/temp")
									.build())
								.target(Target.builder()
									.url("http://localhost:8080")
									.version("V1.0.0")
									.build())
								.manager(Manager.builder()
									.submit(Boolean.TRUE)
									.build())
								.build())
							.build(),
						"Property 'execution.manager.url' should be declared in 'configuration.yml' file"),
				Arguments.of(
						getConfiguration()
							.execution(Execution.builder()
								.delay(Delay.builder()
									.actionInterval(1)
									.waitingTimeout(1)
									.build())
								.directory(Directory.builder()
									.temp("/probato/temp")
									.build())
								.target(Target.builder()
									.url("http://localhost:8080")
									.version("V1.0.0")
									.build())
								.manager(Manager.builder()
									.submit(Boolean.TRUE)
									.url("http://localhost:8080")
									.build())
								.build())
							.build(),
						"Property 'execution.manager.token' should be declared in 'configuration.yml' file"));
	}

	private static ConfigurationBuilder getConfiguration() {
		return Configuration.builder()
				.execution(Execution.builder()
						.increment(1L)
						.screen(Screen.PRINCIPAL)
						.target(Target.builder()
								.url("http://localhost:8080")
								.version("V1.0.0")
								.build())
						.manager(Manager.builder()
								.url("http://localhost:8080")
								.token("token")
								.submit(Boolean.TRUE)
								.build())
						.delay(Delay.builder()
								.waitingTimeout(1)
								.actionInterval(1)
								.build())
						.video(Video.builder()
								.enabled(Boolean.FALSE)
								.frameRate(10D)
								.quality(Quality.MEDIUM)
								.build())
						.directory(Directory.builder()
								.temp("/probato/temp")
								.build())
						.build())
				.browsers(new Browser[] { 
						Browser.builder()
						.type(BrowserType.CHROME)
						.headless(Boolean.TRUE)
						.dimension(new Dimension())
						.build()
				})
				.datasources(null);
	}

}