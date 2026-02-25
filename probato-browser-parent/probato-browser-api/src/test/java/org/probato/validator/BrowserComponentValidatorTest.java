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
import org.probato.model.Execution;
import org.probato.model.Manager;
import org.probato.model.Target;
import org.probato.model.Video;
import org.probato.test.suite.UC00_NoSuite;
import org.probato.test.suite.UC01_Suite;
import org.probato.type.BrowserType;
import org.probato.type.ComponentValidatorType;
import org.probato.type.DimensionMode;
import org.probato.type.Quality;
import org.probato.type.Screen;

@DisplayName("Test - BrowserComponentValidator")
class BrowserComponentValidatorTest {

	@Test
	@DisplayName("Should execute validator successfully")
	void shouldExecuteValidatorSuccessfully() {

		var validators = ComponentValidator.getInstance(ComponentValidatorType.BROWSER);

		validators.forEach(validator -> validator.execute(UC01_Suite.class));

		assertEquals(1, validators.size());
	}

	@ParameterizedTest
	@MethodSource("getInvalidConfigurationData")
	@DisplayName("Should validate configuration data")
	void shouldValidateConfigurationData(Configuration configuration, String message) throws Exception {

		try (MockedStatic<ConfigurationContext> mocked = mockStatic(ConfigurationContext.class)) {

			mocked.when(() -> ConfigurationContext.get(any())).thenReturn(configuration);

			var validators = ComponentValidator.getInstance(ComponentValidatorType.BROWSER);

			var exception = assertThrows(IntegrityException.class,
					() -> validators.forEach(validator -> validator.execute(UC00_NoSuite.class)));

			assertEquals(message, exception.getMessage());
		}
	}

	private static Stream<Arguments> getInvalidConfigurationData() {
		return Stream.of(
				Arguments.of(
						getConfiguration()
							.browsers(new Browser[] {})
							.build(),
						"Property 'browsers' should be declared in 'configuration.yaml' file"),
				Arguments.of(
						getConfiguration()
						.browsers(null)
						.build(),
						"Property 'browsers' should be declared in 'configuration.yaml' file"),
				Arguments.of(
						getConfiguration()
							.browsers(new Browser[] {
								new Browser(null, Boolean.TRUE, new Dimension())
							})
							.build(),
						"Property 'browsers.[0].type' should be declared in 'configuration.yaml' file"),
				Arguments.of(
						getConfiguration()
							.browsers(new Browser[] {
								new Browser(BrowserType.CHROME, Boolean.TRUE, null)
							})
							.build(),
						"Property 'browsers.[0].dimension.mode' should be declared in 'configuration.yaml' file"),
				Arguments.of(
						getConfiguration()
							.browsers(new Browser[] {
								new Browser(BrowserType.CHROME, Boolean.TRUE, new Dimension())
							})
							.build(),
						"Property 'browsers.[0].dimension.mode' should be declared in 'configuration.yaml' file"),
				Arguments.of(
						getConfiguration()
							.browsers(new Browser[] {
								new Browser(BrowserType.CHROME, Boolean.TRUE, new Dimension(null, null, DimensionMode.CUSTOM))
							})
							.build(),
						"Property 'browsers.[0].dimension.height' and 'browsers.[0].dimension.width' should be declared in 'configuration.yaml' file when 'browsers.[0].dimension.mode' equals CUSTOM"),
				Arguments.of(
						getConfiguration()
							.browsers(new Browser[] {
								new Browser(BrowserType.CHROME, Boolean.TRUE, new Dimension(800, null, DimensionMode.CUSTOM))
							})
							.build(),
						"Property 'browsers.[0].dimension.height' and 'browsers.[0].dimension.width' should be declared in 'configuration.yaml' file when 'browsers.[0].dimension.mode' equals CUSTOM"),
				Arguments.of(
						getConfiguration()
							.browsers(new Browser[] {
								new Browser(BrowserType.CHROME, Boolean.TRUE, new Dimension(null, 600, DimensionMode.CUSTOM))
							})
							.build(),
						"Property 'browsers.[0].dimension.height' and 'browsers.[0].dimension.width' should be declared in 'configuration.yaml' file when 'browsers.[0].dimension.mode' equals CUSTOM"));
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
						.build())
				.browsers(new Browser[] {
					new Browser(BrowserType.CHROME, Boolean.TRUE, new Dimension())
				})
				.datasources(null);
	}

}