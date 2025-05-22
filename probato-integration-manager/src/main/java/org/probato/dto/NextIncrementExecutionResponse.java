package org.probato.dto;

import java.util.UUID;

public class NextIncrementExecutionResponse {

	private UUID projectId;
	private Long increment;
	private Long nextIncrement;

	public UUID getProjectId() {
		return projectId;
	}

	public Long getIncrement() {
		return increment;
	}

	public Long getNextIncrement() {
		return nextIncrement;
	}

}