package org.probato.loader;

import java.io.InputStream;
import java.io.SequenceInputStream;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.probato.exception.IntegrityException;
import org.probato.model.Browser;
import org.probato.model.Datasource;
import org.probato.model.Delay;
import org.probato.model.Directory;
import org.probato.model.Execution;
import org.probato.model.Video;
import org.probato.model.type.Screen;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

public class Configuration {

	private static final String CONF_FILE = "configuration.yml";
	private static final String CONF_FILE_NOT_FOUND = "configuration{0}.yml file not found";
	private static final String CONF_PROFILE_FILE = "configuration-{0}.yml";

	private static Configuration instance;

	private Execution execution;
	private Browser[] browsers;
	private Map<String, Datasource> datasources;

	private Configuration() {}

	private Configuration(ConfigurationBuilder builder) {
		this.execution = builder.execution;
		this.browsers = builder.browsers;
		this.datasources = builder.datasources;
	}

	public static Configuration getInstance() {
		return getInstance(null);
	}

	public static Configuration getInstance(Class<?> clazz) {
		if (!isLoaded()) {
			load(clazz);
		}
		return instance;
	}

	public static void clear() {
		instance = null;
	}

	private static boolean isLoaded() {
		return Objects.nonNull(instance);
	}

	private static void load(Class<?> clazz) {

		var profile = clazz.getClassLoader().getResourceAsStream(CONF_FILE);
		if (Objects.isNull(profile)) {
			throw new IntegrityException(CONF_FILE_NOT_FOUND, "");
		}
		
		var profileArg = System.getProperty("profile");
		if (Objects.nonNull(profileArg)) {
			
			var fileName = MessageFormat.format(CONF_PROFILE_FILE, profileArg);
			var profileSec = clazz.getClassLoader().getResourceAsStream(fileName);
			
			if (Objects.isNull(profile)) {
				throw new IntegrityException(CONF_FILE_NOT_FOUND, "-" + "profileArg");
			}
			
			profile = new SequenceInputStream(profile, profileSec);
		}

		loadFile(profile);
	}

	private static void loadFile(InputStream inputStream) {
		var yaml = new Yaml(new Constructor(Configuration.class, new LoaderOptions()));
		instance = yaml.load(inputStream);
		
		initDefaults();
	}

	private static void initDefaults() {
		
		if (Objects.isNull(instance)) {
			instance = new Configuration();
		}
		
		if (Objects.isNull(instance.getExecution())) {
			instance.setExecution(Execution.builder()
					.screen(Screen.PRINCIPAL)
					.build());
		}

		if (Objects.isNull(instance.getExecution().getDelay())) {
			instance.getExecution().setDelay(new Delay());
		}

		if (Objects.isNull(instance.getExecution().getVideo())) {
			instance.getExecution().setVideo(new Video());
		}
		
		if (Objects.isNull(instance.getExecution().getDirectory())) {
			instance.getExecution().setDirectory(new Directory());
		}

		if (Objects.isNull(instance.getBrowsers())) {
			instance.setBrowsers(new Browser [] {});
		}
		
		if (Objects.isNull(instance.getDatasources())) {
			instance.setDatasources(new HashMap<>());
		}
	}

	public Execution getExecution() {
		return execution;
	}

	public void setExecution(Execution execution) {
		this.execution = execution;
	}

	public Browser[] getBrowsers() {
		return browsers;
	}

	public void setBrowsers(Browser[] browsers) {
		this.browsers = browsers;
	}

	public Map<String, Datasource> getDatasources() {
		return datasources;
	}

	public void setDatasources(Map<String, Datasource> datasources) {
		this.datasources = datasources;
	}

	public Datasource getDatasource(String key) {
		return datasources.get(key);
	}

	public static ConfigurationBuilder builder() {
		return new ConfigurationBuilder();
	}

	public static class ConfigurationBuilder {

		private Execution execution;
		private Browser[] browsers;
		private Map<String, Datasource> datasources;

		private ConfigurationBuilder() {
		}

		public ConfigurationBuilder execution(Execution execution) {
			this.execution = execution;
			return this;
		}

		public ConfigurationBuilder browsers(Browser[] browsers) {
			this.browsers = browsers;
			return this;
		}

		public ConfigurationBuilder datasources(Map<String, Datasource> datasources) {
			this.datasources = datasources;
			return this;
		}

		public Configuration build() {
			return new Configuration(this);
		}
	}
}