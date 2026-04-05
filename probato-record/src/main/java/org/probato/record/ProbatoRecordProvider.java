package org.probato.record;

import org.probato.exception.ExecutionException;
import org.probato.model.Dimension;
import org.probato.model.Directory;
import org.probato.model.Target;
import org.probato.model.Video;
import org.probato.type.Screen;

public class ProbatoRecordProvider implements RecordProvider {

	private static final String ERROR_DEFAULT_MSG = "An error occurred while trying to record screen the execution: {0}";

	private final DeleteExecutionData deleteExecutionData;

	public ProbatoRecordProvider() {
		this.deleteExecutionData = new DeleteExecutionData();
	}

	@Override
	public ScreenRecorder createScreenRecord(Screen screen, Video video, Dimension dimension, String outputFile) {
		try {
			return new ScreenRecorder(outputFile, screen, video, dimension);
		} catch (Exception ex) {
			Thread.currentThread().interrupt();
			throw new ExecutionException(ERROR_DEFAULT_MSG, ex.getMessage());
		}
	}

	@Override
	public Screenshot createScreenshot(Screen screen, Dimension dimension, String outputFile) {
		try {
			return new Screenshot(outputFile, screen, dimension);
		} catch (Exception ex) {
			throw new ExecutionException(ERROR_DEFAULT_MSG, ex.getMessage());
		}
	}

	@Override
	public void deleteExecutionData(Target target, Directory directory) {
		deleteExecutionData.execute(target, directory);
	}

}