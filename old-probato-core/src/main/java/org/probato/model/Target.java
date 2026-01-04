package org.probato.model;

import java.util.UUID;

public class Target {

	private UUID projectId;
	private String url;
	private String version;
	
	public Target() {}

	public Target(TargetBuilder builder) {
		this.projectId = builder.projectId;
		this.url = builder.url;
		this.version = builder.version;
	}

	public UUID getProjectId() {
		return projectId;
	}

	public void setProjectId(UUID projectId) {
		this.projectId = projectId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public static TargetBuilder builder() {
		return new TargetBuilder();
	}
	
	public static class TargetBuilder {
		
		private UUID projectId;
		private String url;
		private String version;
		
		private TargetBuilder() {}
		
		public TargetBuilder projectId(UUID projectId) {
			this.projectId = projectId;
			return this;
		}
		
		public TargetBuilder url(String url) {
			this.url = url;
			return this;
		}

		public TargetBuilder version(String version) {
			this.version = version;
			return this;
		}

		public Target build() {
			return new Target(this);
		}
	}

}