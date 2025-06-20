package org.probato.validator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
import org.probato.model.type.DimensionMode;
import org.probato.model.type.Quality;
import org.probato.model.type.Screen;
import org.probato.test.suite.UC00_NoSuite;
import org.probato.test.suite.UC01_Suite;

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

		var config = Configuration.getInstance(UC01_Suite.class);
		var instance = config.getClass().getDeclaredField("instance");
		instance.setAccessible(Boolean.TRUE);
		instance.set(config, configuration);

		var validators = ComponentValidator.getInstance(ComponentValidatorType.BROWSER);

		var exception = assertThrows(IntegrityException.class,
				() -> validators.forEach(validator -> validator.execute(UC00_NoSuite.class)));

		assertEquals(message, exception.getMessage());

		instance.set(configuration, null);
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
							.browsers(new Browser[] { Browser.builder()
								.type(null)
								.headless(Boolean.TRUE)
								.dimension(new Dimension())
								.build() 
							})
							.build(),
						"Property 'browsers[0].type' should be declared in 'configuration.yaml' file"),
				Arguments.of(
						getConfiguration()
							.browsers(new Browser[] { Browser.builder()
								.type(BrowserType.CHROME)
								.headless(Boolean.TRUE)
								.dimension(null)
								.build() 
							})
							.build(),
						"Property 'browsers[0].dimension.mode' should be declared in 'configuration.yaml' file"),
				Arguments.of(
						getConfiguration()
							.browsers(new Browser[] { Browser.builder()
								.type(BrowserType.CHROME)
								.headless(Boolean.TRUE)
								.dimension(Dimension.builder()
										.mode(null)
										.build())
								.build() 
							})
							.build(),
						"Property 'browsers[0].dimension.mode' should be declared in 'configuration.yaml' file"),
				Arguments.of(
						getConfiguration()
							.browsers(new Browser[] { Browser.builder()
								.type(BrowserType.CHROME)
								.headless(Boolean.TRUE)
								.dimension(Dimension.builder()
										.mode(DimensionMode.CUSTOM)
										.width(null)
										.height(null)
										.build())
								.build() 
							})
							.build(),
						"Property 'browsers[0].dimension.height' and 'browsers[0].dimension.width' should be declared in 'configuration.yaml' file when 'browsers[0].dimension.mode' equals CUSTOM"),
				Arguments.of(
						getConfiguration()
							.browsers(new Browser[] { Browser.builder()
								.type(BrowserType.CHROME)
								.headless(Boolean.TRUE)
								.dimension(Dimension.builder()
										.mode(DimensionMode.CUSTOM)
										.width(800)
										.height(null)
										.build())
								.build() 
							})
							.build(),
						"Property 'browsers[0].dimension.height' and 'browsers[0].dimension.width' should be declared in 'configuration.yaml' file when 'browsers[0].dimension.mode' equals CUSTOM"),
				Arguments.of(
						getConfiguration()
							.browsers(new Browser[] { Browser.builder()
								.type(BrowserType.CHROME)
								.headless(Boolean.TRUE)
								.dimension(Dimension.builder()
										.mode(DimensionMode.CUSTOM)
										.width(null)
										.height(600)
										.build())
								.build() 
							})
							.build(),
						"Property 'browsers[0].dimension.height' and 'browsers[0].dimension.width' should be declared in 'configuration.yaml' file when 'browsers[0].dimension.mode' equals CUSTOM"));
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