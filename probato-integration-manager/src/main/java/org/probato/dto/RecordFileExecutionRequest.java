package org.probato.dto;

import java.util.UUID;

public class RecordFileExecutionRequest {

	private UUID projectId;
	private String projectVersion;
	private Long increment;

	private RecordFileExecutionRequest(RecordFileExecutionRequestBuilder builder) {
		this.projectId = builder.projectId;
		this.projectVersion = builder.projectVersion;
		this.increment = builder.increment;
	}
	
	public UUID getProjectId() {
		return projectId;
	}

	public String getProjectVersion() {
		return projectVersion;
	}

	public Long getIncrement() {
		return increment;
	}
	
	public static RecordFileExecutionRequestBuilder builder() {
		return new RecordFileExecutionRequestBuilder();
	}
	
	public static class RecordFileExecutionRequestBuilder {
		
		private UUID projectId;
		private String projectVersion;
		private Long increment;
		
		public RecordFileExecutionRequestBuilder projectId(UUID projectId) {
			this.projectId = projectId;
			return this;
		}
		
		public RecordFileExecutionRequestBuilder projectVersion(String projectVersion) {
			this.projectVersion = projectVersion;
			return this;
		}

		public RecordFileExecutionRequestBuilder increment(Long increment) {
			this.increment = increment;
			return this;
		}
		
		public RecordFileExecutionRequest build() {
			return new RecordFileExecutionRequest(this);
		}
	}

}