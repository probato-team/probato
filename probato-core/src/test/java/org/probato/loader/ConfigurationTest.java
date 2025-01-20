package org.probato.loader;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.probato.model.type.BrowserType;
import org.probato.model.type.DimensionMode;
import org.probato.model.type.Quality;
import org.probato.model.type.Screen;

@DisplayName("Test -> Configuration")
class ConfigurationTest {

	@BeforeEach
	void setup() {
		Configuration.getInstance(getClass());
	}

	@Test
	@DisplayName("Should get temporary folder successfully")
	void shouldGetTempDirSuccessfully() {
		var configuration = Configuration.getInstance(getClass());
		assertEquals("C:/temp", configuration.getExecution().getDirectory().getTemp());
	}

	@Test
	@DisplayName("Should get project data successfully")
	void shouldGetProjectSuccessfully() {

		var configuration = Configuration.getInstance(getClass());
		var project = configuration.getExecution().getTarget();

		assertAll("Validate data", 
				() -> assertEquals("http://google.com", project.getUrl()),
				() -> assertEquals("1.0.0", project.getVersion()));
	}

	@Test
	@DisplayName("Should get execution data successfully")
	void shouldGetExecutionSuccessfully() {

		var configuration = Configuration.getInstance(getClass());
		var execution = configuration.getExecution();

		assertAll("Validate data", 
				() -> assertTrue(execution.getManager().isSubmit()),
				() -> assertEquals("http://localhost:9999", execution.getManager().getUrl()),
				() -> assertEquals("89caa226caa52436d25a0f94bb42ee7ef0ac92c42154c6fe775ba230ad83417b", execution.getManager().getToken()),
				() -> assertEquals(Screen.PRINCIPAL, execution.getScreen()));
	}

	@Test
	@DisplayName("Should get delay data successfully")
	void shouldGetDelaySuccessfully() {

		var configuration = Configuration.getInstance(getClass());
		var delay = configuration.getExecution().getDelay();

		assertAll("Validate data", 
				() -> assertEquals(1, delay.getWaitingTimeout()),
				() -> assertEquals(1, delay.getActionInterval()));
	}
	
	@Test
	@DisplayName("Should get video data successfully")
	void shouldGetVideoSuccessfully() {

		var configuration = Configuration.getInstance(getClass());
		var video = configuration.getExecution().getVideo();

		assertAll("Validate data", 
				() -> assertEquals(40D, video.getFrameRate()),
				() -> assertTrue(video.isEnabled()),
				() -> assertEquals(Quality.HIGH, video.getQuality()));
	}

	@Test
	@DisplayName("Should get browsers data successfully")
	void shouldGetBrowsersSuccessfully() {

		var configuration = Configuration.getInstance(getClass());
		var browsers = configuration.getBrowsers();

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

		var configuration = Configuration.getInstance(getClass());
		var datasources = configuration.getDatasources();
		var datasourceName = "probato";
		var datasource = datasources.get(datasourceName);

		assertAll("Validate data", 
				() -> assertEquals(datasourceName, datasources.keySet().iterator().next()),
				() -> assertEquals("jdbc:h2:mem:testdb", datasource.getUrl()),
				() -> assertEquals("org.h2.Driver", datasource.getDriver()),
				() -> assertEquals("sa", datasource.getUsername()),
				() -> assertEquals("password", datasource.getPassword()));
	}
}