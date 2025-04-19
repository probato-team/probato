package org.probato.engine.execution;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Optional;
import java.util.UUID;

import org.probato.core.loader.Configuration;
import org.probato.engine.execution.builder.Execution;
import org.probato.engine.execution.builder.Script;
import org.probato.engine.execution.builder.Suite;
import org.probato.entity.model.Dimension;
import org.probato.exception.ExecutionException;
import org.probato.service.RecordService;
import org.probato.util.ConverterUtil;
import org.probato.util.FileUtil;

class ExecutionServiceImpl implements ExecutionService {

	private static final String DIRECTORY = "{0}/{1}/{2}";
	private static final String SUITE_FILE_JSON = "000-{0}.json";
	private static final String SCRIPT_FILE_JSON = "001-{0}.json";
	private static final String FILE_JSON = "{0}.json";
	private static final String FILE_MP4 = "{0}.mp4";
	private static final String FILE_JPG = "{0}.jpg";
	private static final String ERROR_DEFAULT_MSG = "An error occurred while executing: {0}";

	private final UUID executionId;
	private final Configuration configuration;
	private final Optional<RecordService> recordService;

	protected ExecutionServiceImpl(Class<?> clazz) {
		this.executionId = UUID.randomUUID();
		this.configuration = Configuration.getInstance(clazz);
		this.recordService = RecordService.getInstance();
	}

	@Override
	public void save(Suite suite, Script script, Execution execution) {
		try {

			var directory = buildPath(
					DIRECTORY, 
					configuration.getExecution().getDirectory().getTemp(), 
					suite.getProjectId(), 
					suite.getProjectVersion(), 
					execution.getId());
			
			saveSuite(suite, directory);
			saveScript(script, directory);
			saveExecution(execution, directory);

		} catch (IOException ex) {
			throw new Error(ex.getMessage()); // NOSONAR
		}
	}

	@Override
	public UUID captureScreen(Dimension dimension) {
		UUID ret = null;
		try {
			
			if (recordService.isPresent() && isEnabled()) {
				
				var service = recordService.get();
				var projectId = configuration.getExecution().getTarget().getProjectId();
				var projectVersion = configuration.getExecution().getTarget().getVersion();
				var tempDir = configuration.getExecution().getDirectory().getTemp();
				var directory = buildPath(DIRECTORY, tempDir, projectId, projectVersion);
				var name = buildPath(FILE_JPG, executionId);
				
				FileUtil.createTempDir(directory);
				
				service.screenshot(directory.concat("/").concat(name), dimension);
				
				ret = executionId;
			}

		} catch (Exception ex) {
			throw new ExecutionException(ERROR_DEFAULT_MSG, ex.getMessage());
		}
		
		return ret;
	}
	
	@Override
	public UUID startRecording(Dimension dimension) {
		UUID ret = null;
		try {
			

			if (recordService.isPresent() && isEnabled()) {
				
				var service = recordService.get();
				var projectId = configuration.getExecution().getTarget().getProjectId();
				var projectVersion = configuration.getExecution().getTarget().getVersion();
				var tempDir = configuration.getExecution().getDirectory().getTemp();
				var directory = buildPath(DIRECTORY, tempDir, projectId, projectVersion);
				var name = buildPath(FILE_MP4, executionId);
				
				FileUtil.createTempDir(directory);
				
				service.start(directory.concat("/").concat(name), dimension);
				
				ret = executionId;
			}

		} catch (Exception ex) {
			throw new ExecutionException(ERROR_DEFAULT_MSG, ex.getMessage());
		}
		
		return ret;
	}

	@Override
	public void endRecording() {
		try {

			recordService.ifPresent(RecordService::stop);

		} catch (Exception ex) {
			throw new ExecutionException(ERROR_DEFAULT_MSG, ex.getMessage());
		}
	}
	
	@Override
	public UUID getExecutionId() {
		return executionId;
	}
	
	private boolean isEnabled() {
		return configuration.getExecution().getVideo().isEnabled();
	}

	private void saveSuite(Suite suite, String directory) throws IOException {
		var name = buildPath(SUITE_FILE_JSON, suite.getCode());
		var json = ConverterUtil.toJson(suite);
		FileUtil.save(directory, name, json);
	}

	private void saveScript(Script script, String directory) throws IOException {
		var name = buildPath(SCRIPT_FILE_JSON, script.getCode());
		var json = ConverterUtil.toJson(script);
		FileUtil.save(directory, name, json);
	}

	private void saveExecution(Execution execution, String directory) throws IOException {
		var name = buildPath(FILE_JSON, execution.getId());
		var json = ConverterUtil.toJson(execution);
		FileUtil.save(directory, name, json);
	}

	private String buildPath(String pattern, Object... params) {
		return MessageFormat.format(pattern, params);
	}
}