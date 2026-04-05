package org.probato.service;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Objects;
import java.util.ServiceLoader;
import java.util.ServiceLoader.Provider;
import java.util.UUID;

import org.probato.engine.builder.Execution;
import org.probato.engine.builder.Script;
import org.probato.engine.builder.Suite;
import org.probato.exception.ExecutionException;
import org.probato.loader.ConfigurationContext;
import org.probato.model.Configuration;
import org.probato.model.Dimension;
import org.probato.model.Directory;
import org.probato.model.Target;
import org.probato.record.RecordProvider;
import org.probato.record.ScreenRecorder;
import org.probato.record.Screenshot;
import org.probato.utils.ConverterUtils;
import org.probato.utils.FileUtils;

public class RecordService {

	private static final String DIRECTORY = "{0}/{1}/{2}";
	private static final String SUITE_FILE_JSON = "000-{0}.json";
	private static final String SCRIPT_FILE_JSON = "001-{0}.json";
	private static final String EXECUTION_FILE_JSON = "{0}.json";
	private static final String FILE_MP4 = "{0}.mp4";
	private static final String FILE_JPG = "{0}.jpg";
	private static final String ERROR_DEFAULT_MSG = "An error occurred while executing: {0}";

	private Configuration configuration;
	private RecordProvider provider;

	private RecordService() {
		this.configuration = ConfigurationContext.get();
		load();
	}

	public static RecordService get() {
		return new RecordService();
	}

	public ScreenRecorder createScreenRecord(Dimension dimension, UUID executionId) {
		try {

			var directory = createDirectory();

			FileUtils.createTempDir(directory);

			var execution = configuration.getExecution();
			var screen = execution.getScreen();
			var video = execution.getVideo();
			var name = buildPath(FILE_MP4, executionId);
			var outputFile = directory.concat("/").concat(name);

			return provider.createScreenRecord(screen, video, dimension, outputFile);

		} catch (Exception ex) {
			throw new ExecutionException(ERROR_DEFAULT_MSG, ex.getMessage());
		}
	}

	public Screenshot createScreenshot(Dimension dimension, String executionId) {
		try {

			var directory = createDirectory();

			FileUtils.createTempDir(directory);

			var execution = configuration.getExecution();
			var screen = execution.getScreen();
			var name = buildPath(FILE_JPG, executionId);
			var outputFile = directory.concat("/").concat(name);

			return provider.createScreenshot(screen, dimension, outputFile);

		} catch (Exception ex) {
			throw new ExecutionException(ERROR_DEFAULT_MSG, ex.getMessage());
		}
	}

	public void deleteExecutionData(Target target, Directory directory) {
		try {

			provider.deleteExecutionData(target, directory);

		} catch (Exception ex) {
			throw new ExecutionException(ERROR_DEFAULT_MSG, ex.getMessage());
		}
	}

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

	private String createDirectory() {

		var projectId = configuration.getExecution().getTarget().getProjectId();
		var projectVersion = configuration.getExecution().getTarget().getVersion();
		var tempDir = configuration.getExecution().getDirectory().getTemp();

		return buildPath(DIRECTORY, tempDir, projectId, projectVersion);
	}

	private void load() {
		if (Objects.isNull(provider)) {
			provider = ServiceLoader.load(RecordProvider.class)
					.stream()
					.map(Provider::get)
					.findFirst()
					.orElse(provider);
		}
	}

	private void saveSuite(Suite suite, String directory) throws IOException {
		var name = buildPath(SUITE_FILE_JSON, suite.getCode());
		var json = ConverterUtils.toJson(suite);
		FileUtils.save(directory, name, json);
	}

	private void saveScript(Script script, String directory) throws IOException {
		var name = buildPath(SCRIPT_FILE_JSON, script.getCode());
		var json = ConverterUtils.toJson(script);
		FileUtils.save(directory, name, json);
	}

	private void saveExecution(Execution execution, String directory) throws IOException {
		var name = buildPath(EXECUTION_FILE_JSON, execution.getId());
		var json = ConverterUtils.toJson(execution);
		FileUtils.save(directory, name, json);
	}

	private String buildPath(String pattern, Object... params) {
		return MessageFormat.format(pattern, params);
	}

}