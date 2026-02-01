package org.probato.validator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.MockedStatic;
import org.probato.exception.IntegrityException;
import org.probato.loader.ConfigurationContext;
import org.probato.model.Browser;
import org.probato.model.Configuration;
import org.probato.model.Configuration.ConfigurationBuilder;
import org.probato.model.Delay;
import org.probato.model.Dimension;
import org.probato.model.Directory;
import org.probato.model.Execution;
import org.probato.model.Manager;
import org.probato.model.Target;
import org.probato.model.Video;
import org.probato.test.suite.UC00_NoSuite;
import org.probato.test.suite.UC01_Suite;
import org.probato.type.BrowserType;
import org.probato.type.ComponentValidatorType;
import org.probato.type.Quality;
import org.probato.type.Screen;

@DisplayName("UT - ConfigurationComponentValidator")
class ConfigurationComponentValidatorTest {

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

		try (MockedStatic<ConfigurationContext> mocked = mockStatic(ConfigurationContext.class)) {

			mocked.when(() -> ConfigurationContext.get(any())).thenReturn(configuration);

			var validators = ComponentValidator.getInstance(ComponentValidatorType.CONFIGURATION);

			var exception = assertThrows(IntegrityException.class,
					() -> validators.forEach(validator -> validator.execute(UC00_NoSuite.class)));

			assertEquals(message, exception.getMessage());
		}
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
						.screen(Screen.PRIMARY)
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