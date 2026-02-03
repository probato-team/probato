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
import org.probato.model.Delay;
import org.probato.model.Dimension;
import org.probato.model.Directory;
import org.probato.model.Execution;
import org.probato.model.Manager;
import org.probato.model.Target;
import org.probato.model.Video;
import org.probato.model.Configuration.ConfigurationBuilder;
import org.probato.test.suite.UC01_Suite;
import org.probato.type.BrowserType;
import org.probato.type.ComponentValidatorType;
import org.probato.type.Quality;
import org.probato.type.Screen;

@DisplayName("Test - BrowserConfigurationComponentValidator")
class BrowserConfigurationValidationComponentTest {

	@Test
	@DisplayName("Should execute validator successfully")
	void shouldExecuteValidatorSuccessfully() {

		var validators = ComponentValidator.getInstance(ComponentValidatorType.CONFIGURATION);

		validators.forEach(validator -> validator.execute(UC01_Suite.class));

		assertEquals(3, validators.size());
	}

	@ParameterizedTest
	@MethodSource("getInvalidConfigurationData")
	@DisplayName("Should validate configuration data")
	void shouldValidateConfigurationData(Configuration configuration, String message) throws Exception {

		try (MockedStatic<ConfigurationContext> mocked = mockStatic(ConfigurationContext.class)) {

			mocked.when(() -> ConfigurationContext.get(any())).thenReturn(configuration);

			var validators = ComponentValidator.getInstance(ComponentValidatorType.CONFIGURATION);

			var exception = assertThrows(IntegrityException.class,
					() -> validators.forEach(validator -> validator.execute(UC01_Suite.class)));

			assertEquals(message, exception.getMessage());
		}
	}

	private static Stream<Arguments> getInvalidConfigurationData() {
		return Stream.of(
				Arguments.of(
						getConfiguration()
							.execution(null)
							.build(),
						"Property 'execution' should be declared in 'configuration.yaml' file"),
				Arguments.of(
						getConfiguration()
							.execution(Execution.builder().build())
							.build(),
						"Property 'execution.target.url' should be declared in 'configuration.yaml' file"),
				Arguments.of(
						getConfiguration()
							.execution(Execution.builder()
								.target(Target.builder().build())
								.build())
						.build(),
						"Property 'execution.target.url' should be declared in 'configuration.yaml' file"),
				Arguments.of(
						getConfiguration()
							.execution(Execution.builder()
								.target(Target.builder()
									.url("http://localhost:8080")
									.build())
								.build())
						.build(),
						"Property 'execution.target.version' should be declared in 'configuration.yaml' file"));
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
						.delay(new Delay(1, 1))
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
					new Browser(BrowserType.CHROME, Boolean.TRUE, new Dimension())
				})
				.datasources(null);
	}

}