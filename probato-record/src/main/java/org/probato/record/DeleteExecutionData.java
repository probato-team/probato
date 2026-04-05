package org.probato.record;

import java.io.File;
import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.probato.exception.ExecutionException;
import org.probato.model.Directory;
import org.probato.model.Target;
import org.probato.utils.FileUtils;

public class DeleteExecutionData {

	protected static final String ERRO_INVOKE_MSG = "An error occurred when trying to invoke the web application: {0}";
	private static final String DIRECTORY = "{0}\\{1}\\{2}";

	public void execute(Target target, Directory directory) {
		try {

			if (!directory.isKeepClean()) return;

			var files = getExecutionFiles(target, directory);
			for (var file : files) {
				FileUtils.delete(file);
			}

		} catch (Exception ex) { // NOSONAR
			throw new ExecutionException(ERRO_INVOKE_MSG, ex.getMessage());
		}
	}

	protected String getTempDir(Directory directory) {
		return Optional.ofNullable(directory)
				.map(Directory::getTemp)
				.orElse(null);
	}

	protected UUID getProjectId(Target target) {
		return Optional.ofNullable(target)
				.map(Target::getProjectId)
				.orElse(null);
	}

	protected String getProjectVersion(Target target) {
		return Optional.ofNullable(target)
				.map(Target::getVersion)
				.orElse(null);
	}

	private List<File> getExecutionFiles(Target target, Directory directory) {
		var path = buildUrl(DIRECTORY, getTempDir(directory), getProjectId(target), getProjectVersion(target));
		return FileUtils.loadFiles(path);
	}

	protected String buildUrl(String pattern, Object ... params) {
		return MessageFormat.format(pattern, params);
	}

}