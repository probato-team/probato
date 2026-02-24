package org.probato.model;

import java.util.HashMap;
import java.util.Map;

import org.probato.type.Screen;

public class Configuration {

	private Execution execution;
	private Browser[] browsers;
	private Map<String, Datasource> datasources;

	public Configuration() {}

	Configuration(Execution execution, Browser[] browsers, Map<String, Datasource> datasources) {
		this();
		this.execution = execution;
		this.browsers = browsers;
		this.datasources = datasources;
	}

	private Configuration(ConfigurationBuilder builder) {
		this();
		this.execution = builder.execution;
		this.browsers = builder.browsers;
		this.datasources = builder.datasources;
	}

	public void initDefaults() {

		if (execution == null) {
			execution = Execution.builder().screen(Screen.PRIMARY).build();
		}

		if (execution.getDelay() == null) {
			execution.setDelay(new Delay());
		}

		if (execution.getVideo() == null) {
			execution.setVideo(new Video());
		}

		if (execution.getDirectory() == null) {
			execution.setDirectory(new Directory());
		}

		if (browsers == null) {
			browsers = new Browser[] {};
		}

		if (datasources == null) {
			datasources = new HashMap<>();
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

		private ConfigurationBuilder() {}

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