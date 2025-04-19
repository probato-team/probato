package org.probato.engine.execution;

import java.util.UUID;

import org.probato.engine.execution.builder.Execution;
import org.probato.engine.execution.builder.Script;
import org.probato.engine.execution.builder.Suite;
import org.probato.entity.model.Dimension;

public interface ExecutionService {

	public static ExecutionService getInstance(Class<?> clazz) {
		return new ExecutionServiceImpl(clazz);
	}

	public void save(Suite suite, Script script, Execution execution);

	public UUID captureScreen(Dimension dimension);
	
	public UUID startRecording(Dimension dimension);

	public void endRecording();
	
	public UUID getExecutionId();
	
}