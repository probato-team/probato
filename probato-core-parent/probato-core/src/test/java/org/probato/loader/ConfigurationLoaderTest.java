package org.probato.loader;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("UT – ConfigurationLoader")
class ConfigurationLoaderTest {

	private final ConfigurationLoader loader = new ConfigurationLoader();

	@AfterEach
	void cleanup() {

		System.clearProperty("profile");
		System.clearProperty("probato.execution.delay.waitingTimeout");
		System.clearProperty("probato.execution.target.url");
	}

	@Test
	@DisplayName("Should only load the configuration.yml file when no profile is specified")
	void shouldLoadBaseConfigurationOnly() {

		var config = loader.load(getClass(), List.of());

		assertAll("Validate loader",
				() -> assertEquals("http://base", config.getExecution().getTarget().getUrl()),
				() -> assertEquals(5000, config.getExecution().getDelay().getWaitingTimeout()));
	}

	@Test
	@DisplayName("Should overwrite base values ​​with a single profile")
	void shouldOverrideBaseWithSingleProfile() {

		var config = loader.load(getClass(), List.of("dev"));

		assertAll("Validate loader",
				() -> assertEquals("http://dev", config.getExecution().getTarget().getUrl()),
				() -> assertEquals(5000, config.getExecution().getDelay().getWaitingTimeout()));
	}

	@Test
	@DisplayName("Should merge multiple profiles cumulatively")
	void shouldMergeMultipleProfiles() {

		var config = loader.load(getClass(), List.of("dev", "ci"));

		assertAll("Validate loader",
				() -> assertEquals("http://dev", config.getExecution().getTarget().getUrl()),
				() -> assertEquals(1000, config.getExecution().getDelay().getWaitingTimeout()));
	}

	@Test
	@DisplayName("Should respect the order of the profiles")
	void shouldRespectProfileOrder() {

		var config = loader.load(getClass(), List.of("ci", "dev"));

		assertAll("Validate loader",
				() -> assertEquals("http://dev", config.getExecution().getTarget().getUrl()),
				() -> assertEquals(1000, config.getExecution().getDelay().getWaitingTimeout()));
	}

	@Test
	@DisplayName("Should load system properties and override any profile")
	void shouldOverrideUsingSystemProperties() {

		System.setProperty("probato.execution.delay.waitingTimeout", "200");
		System.setProperty("probato.execution.target.url", "http://system");

		var config = loader.load(getClass(), List.of("dev", "ci"));

		assertAll("Validate loader",
				() -> assertEquals("http://system", config.getExecution().getTarget().getUrl()),
				() -> assertEquals(200, config.getExecution().getDelay().getWaitingTimeout()));
	}

	@Test
	@DisplayName("Should ignored load System properties outside the probato prefix")
	void shouldIgnoreNonProbatoSystemProperties() {

		System.setProperty("java.version", "999");

		var config = loader.load(getClass(), List.of());

		assertAll("Validate loader",
				() -> assertEquals("http://base", config.getExecution().getTarget().getUrl()),
				() -> assertEquals(5000, config.getExecution().getDelay().getWaitingTimeout()));
	}

}