package org.probato.loader;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.probato.model.Configuration;
import org.probato.type.BrowserType;
import org.probato.type.DimensionMode;
import org.probato.type.Quality;
import org.probato.type.Screen;

@DisplayName("UT - ConfigurationContext")
class ConfigurationContextTest {

	@BeforeEach
	void cleanup() {
		System.clearProperty("profile");
		ConfigurationContext.clearProfiles("dev", "ci");
		ConfigurationContext.clear();
	}

	@Test
	void shouldLoadBaseConfigurationWhenNoProfileIsDefined() {

		var config = ConfigurationContext.get();

		assertAll("Validate data",
				() -> assertEquals("http://base", config.getExecution().getTarget().getUrl()),
				() -> assertEquals(5000, config.getExecution().getDelay().getWaitingTimeout()));
	}

	@DisplayName("Should get project data successfully")
	@ParameterizedTest
	@MethodSource("getProfile")
	void shouldOverrideBaseConfigurationWithSingleProfile(String profiles, String urlTarget, Integer waiting) {

		System.setProperty("profile", profiles);

		var config = ConfigurationContext.get();

		assertAll("Validate data",
				() -> assertEquals(urlTarget, config.getExecution().getTarget().getUrl()),
				() -> assertEquals(waiting, config.getExecution().getDelay().getWaitingTimeout()));
	}

	private static Stream<Arguments> getProfile() {
		return Stream.of(
				Arguments.of("dev", "http://dev", 5000),
				Arguments.of("ci", "http://base", 1000),
				Arguments.of("dev,ci", "http://dev", 1000),
				Arguments.of("ci,dev", "http://dev", 1000));
	}

	@Test
	@DisplayName("Should get temporary folder successfully")
	void shouldGetTempDirSuccessfully() {

		var config = ConfigurationContext.get();

		assertEquals("D:/temp", config.getExecution().getDirectory().getTemp());
	}

	@Test
	@DisplayName("Should get project data successfully")
	void shouldGetProjectSuccessfully() {

		var config = ConfigurationContext.get();
		var project = config.getExecution().getTarget();

		assertAll("Validate data",
				() -> assertEquals("http://base", project.getUrl()),
				() -> assertEquals("1.0.0", project.getVersion()));
	}

	@Test
	@DisplayName("Should get execution data successfully")
	void shouldGetExecutionSuccessfully() {

		var config = ConfigurationContext.get();
		var execution = config.getExecution();

		assertAll("Validate data",
				() -> assertTrue(execution.getManager().isSubmit()),
				() -> assertEquals("http://localhost:8099", execution.getManager().getUrl()),
				() -> assertEquals("89caa226caa52436d25a0f94bb42ee7ef0ac92c42154c6fe775ba230ad83417b", execution.getManager().getToken()),
				() -> assertEquals(Screen.PRIMARY, execution.getScreen()));
	}

	@Test
	@DisplayName("Should get delay data successfully")
	void shouldGetDelaySuccessfully() {

		var config = ConfigurationContext.get();
		var delay = config.getExecution().getDelay();

		assertAll("Validate data",
				() -> assertEquals(5000, delay.getWaitingTimeout()),
				() -> assertEquals(1, delay.getActionInterval()));
	}

	@Test
	@DisplayName("Should get video data successfully")
	void shouldGetVideoSuccessfully() {

		var config = ConfigurationContext.get();
		var video = config.getExecution().getVideo();

		assertAll("Validate data",
				() -> assertEquals(40D, video.getFrameRate()),
				() -> assertTrue(video.isEnabled()),
				() -> assertEquals(Quality.HIGH, video.getQuality()));
	}

	@Test
	@DisplayName("Should get browsers data successfully")
	void shouldGetBrowsersSuccessfully() {

		var config = ConfigurationContext.get();
		var browsers = config.getBrowsers();

		assertAll("Validate data",
				() -> assertEquals(BrowserType.CHROME, browsers[0].getType()),
				() -> assertTrue(browsers[0].isHeadless()),
				() -> assertEquals(DimensionMode.FULLSCREEN, browsers[0].getDimension().getMode()),
				() -> assertEquals(BrowserType.FIREFOX, browsers[1].getType()),
				() -> assertFalse(browsers[1].isHeadless()),
				() -> assertEquals(DimensionMode.MAXIMIZED, browsers[1].getDimension().getMode()),
				() -> assertEquals(BrowserType.EDGE, browsers[2].getType()),
				() -> assertFalse(browsers[2].isHeadless()),
				() -> assertEquals(DimensionMode.CUSTOM, browsers[2].getDimension().getMode()),
				() -> assertEquals(800, browsers[2].getDimension().getWidth()),
				() -> assertEquals(600, browsers[2].getDimension().getHeight()));
	}

	@Test
	@DisplayName("Should get datasource data successfully")
	void shouldGetDatasourceSuccessfully() {

		var config = ConfigurationContext.get();
		var datasources = config.getDatasources();
		var datasourceName = "probato";
		var datasource = datasources.get(datasourceName);

		assertAll("Validate data",
				() -> assertNotNull(config.getDatasource("probato")),
				() -> assertEquals(datasourceName, datasources.keySet().iterator().next()),
				() -> assertEquals("jdbc:h2:mem:testdb", datasource.getUrl()),
				() -> assertEquals("org.h2.Driver", datasource.getDriver()),
				() -> assertEquals("sa", datasource.getUsername()),
				() -> assertEquals("password", datasource.getPassword()));
	}

	@Test
	@DisplayName("Should get init defaults data successfully")
	void shouldGetInitDefaultsSuccessfully() {

		var config = new Configuration();
		config.initDefaults();

		assertAll("Validate data",
				() -> assertNotNull(config.getExecution()),
				() -> assertNotNull(config.getExecution().getDelay()),
				() -> assertNotNull(config.getExecution().getVideo()),
				() -> assertNotNull(config.getExecution().getDirectory()),
				() -> assertNotNull(config.getBrowsers()),
				() -> assertNotNull(config.getDatasources()));
	}

}