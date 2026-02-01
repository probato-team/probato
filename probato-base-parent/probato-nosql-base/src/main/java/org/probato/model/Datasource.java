package org.probato.model;

public class Datasource {

	private String url;
	private String schema;
	private String driver;
	private String username;
	private String password;

	public Datasource() {}

	public Datasource(String url, String schema, String driver, String username, String password) {
		this();
		this.url = url;
		this.schema = schema;
		this.driver = driver;
		this.username = username;
		this.password = password;
	}

	public Datasource(DatasourceBuilder builder) {
		this();
		this.url = builder.url;
		this.schema = builder.schema;
		this.driver = builder.driver;
		this.username = builder.username;
		this.password = builder.password;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public static DatasourceBuilder builder() {
		return new DatasourceBuilder();
	}

	public static class DatasourceBuilder {

		private String url;
		private String schema;
		private String driver;
		private String username;
		private String password;

		private DatasourceBuilder() {}

		public DatasourceBuilder url(String url) {
			this.url = url;
			return this;
		}

		public DatasourceBuilder schema(String schema) {
			this.schema = schema;
			return this;
		}

		public DatasourceBuilder driver(String driver) {
			this.driver = driver;
			return this;
		}

		public DatasourceBuilder username(String username) {
			this.username = username;
			return this;
		}

		public DatasourceBuilder password(String password) {
			this.password = password;
			return this;
		}

		public Datasource build() {
			return new Datasource(this);
		}
	}

}