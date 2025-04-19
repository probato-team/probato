package org.probato.validator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.probato.core.loader.Configuration;
import org.probato.core.loader.Configuration.ConfigurationBuilder;
import org.probato.entity.model.Browser;
import org.probato.entity.model.Delay;
import org.probato.entity.model.Dimension;
import org.probato.entity.model.Directory;
import org.probato.entity.model.Execution;
import org.probato.entity.model.Manager;
import org.probato.entity.model.Target;
import org.probato.entity.model.Video;
import org.probato.entity.type.BrowserType;
import org.probato.entity.type.ComponentValidatorType;
import org.probato.entity.type.Quality;
import org.probato.entity.type.Screen;
import org.probato.exception.IntegrityException;
import org.probato.test.suite.UC00_NoSuite;
import org.probato.test.suite.UC01_Suite;

@DisplayName("Test -> ConfigurationComponentValidator")
class ConfigurationComponentValidatorTest {

	@Test
	@DisplayName("Should execute validator successfully")
	void shouldExecuteValidatorSuccessfully() {

		var validators = ComponentValidator.getInstance(ComponentValidatorType.CONFIGURATION);

		validators.forEach(validator -> validator.execute(UC01_Suite.class));

		assertEquals(1, validators.size());
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
				() -> validators.forEach(validator -> validator.execute(UC00_NoSuite.class)));

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
								.screen(null)
								.build())
							.build(),
							"Property 'execution.screen' should be declared in 'configuration.yml' file"),
				Arguments.of(
						getConfiguration()
						.execution(Execution.builder()
							.target(Target.builder()
								.url("http://localhost:8080")
								.version("V1.0.0")
								.build())
							.manager(Manager.builder()
								.submit(Boolean.TRUE)
								.url("http://localhost:8080")
								.token("token")
								.build())
							.build())
						.build(),
						"Property 'execution.delay.actionInterval' should be declared in 'configuration.yml' file"),
				Arguments.of(
						getConfiguration()
						.execution(Execution.builder()
							.target(Target.builder()
								.url("http://localhost:8080")
								.version("V1.0.0")
								.build())
							.manager(Manager.builder()
								.submit(Boolean.TRUE)
								.url("http://localhost:8080")
								.token("token")
								.build())
							.delay(Delay.builder().build())
							.build())
						.build(),
						"Property 'execution.delay.actionInterval' should be declared in 'configuration.yml' file"),
				Arguments.of(
						getConfiguration()
						.execution(Execution.builder()
							.target(Target.builder()
								.url("http://localhost:8080")
								.version("V1.0.0")
								.build())
							.manager(Manager.builder()
								.submit(Boolean.TRUE)
								.url("http://localhost:8080")
								.token("token")
								.build())
							.delay(Delay.builder()
								.actionInterval(1000)
								.build())
							.build())
						.build(),
						"Property 'execution.delay.waitingTimeout' should be declared in 'configuration.yml' file"),
				Arguments.of(
						getConfiguration()
						.execution(Execution.builder()
								.target(Target.builder()
										.url("http://localhost:8080")
										.version("V1.0.0")
										.build())
								.manager(Manager.builder()
										.submit(Boolean.TRUE)
										.url("http://localhost:8080")
										.token("token")
										.build())
								.delay(Delay.builder()
										.actionInterval(1000)
										.waitingTimeout(1000)
										.build())
								.build())
						.build(),
						"Property 'execution.directory.temp' should be declared in 'configuration.yml' file"),
				Arguments.of(
						getConfiguration()
						.execution(Execution.builder()
								.target(Target.builder()
										.url("http://localhost:8080")
										.version("V1.0.0")
										.build())
								.manager(Manager.builder()
										.submit(Boolean.TRUE)
										.url("http://localhost:8080")
										.token("token")
										.build())
								.delay(Delay.builder()
										.actionInterval(1000)
										.waitingTimeout(1000)
										.build())
								.directory(Directory.builder().build())
								.build())
						.build(),
						"Property 'execution.directory.temp' should be declared in 'configuration.yml' file"));
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
								.temp("/testano/temp")
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