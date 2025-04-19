package org.probato.entity.model;

public class Manager {

	private String url;
	private String token;
	private boolean submit = Boolean.FALSE;
	
	public Manager() {}

	private Manager(ProjectBuilder builder) {
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

	public static ProjectBuilder builder() {
		return new ProjectBuilder();
	}
	
	public static class ProjectBuilder {
		
		private String url;
		private String token;
		private boolean submit = Boolean.FALSE;
		
		private ProjectBuilder() {}
		
		public ProjectBuilder url(String url) {
			this.url = url;
			return this;
		}

		public ProjectBuilder token(String token) {
			this.token = token;
			return this;
		}
		
		public ProjectBuilder submit(Boolean submit) {
			this.submit = submit;
			return this;
		}
		
		public Manager build() {
			return new Manager(this);
		}
	}

}