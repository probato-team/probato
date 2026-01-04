package org.probato.integration.manager;

import java.io.File;
import java.util.List;

import org.probato.model.type.ExecutionPhase;
import org.probato.util.FileUtil;

public class DeleteExecutionDataFileService extends AbstractApiService {

	private static final String DIRECTORY = "{0}/{1}/{2}";

	@Override
	public ExecutionPhase getExecutionPhase() {
		return ExecutionPhase.AFTER_ALL;
	}

	public void execute() throws Exception {
		var files = getExecutionFiles();
		for (var file : files) {
			FileUtil.delete(file);
		}
	}

	private List<File> getExecutionFiles() {
		var path = buildUrl(DIRECTORY, getTempDir(), getProjectId(), getProjectVersion());
		return FileUtil.loadFiles(path);
	}

}