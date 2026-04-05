package org.probato.model;

public class Manager {

	private String url;
	private String token;
	private boolean submit = Boolean.FALSE;

	public Manager() {}

	public Manager(String url, String token, Boolean submit) {
		this();
		this.url = url;
		this.token = token;
		this.submit = submit;
	}

	private Manager(ManagerBuilder builder) {
		this();
		this.url = builder.url;
		this.token = builder.token;
		this.submit = builder.submit;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public boolean isSubmit() {
		return submit;
	}

	public void setSubmit(boolean submit) {
		this.submit = submit;
	}

	public static ManagerBuilder builder() {
		return new ManagerBuilder();
	}

	public static class ManagerBuilder {

		private String url;
		private String token;
		private boolean submit = Boolean.FALSE;

		private ManagerBuilder() {}

		public ManagerBuilder url(String url) {
			this.url = url;
			return this;
		}

		public ManagerBuilder token(String token) {
			this.token = token;
			return this;
		}

		public ManagerBuilder submit(Boolean submit) {
			this.submit = submit;
			return this;
		}

		public Manager build() {
			return new Manager(this);
		}
	}

}