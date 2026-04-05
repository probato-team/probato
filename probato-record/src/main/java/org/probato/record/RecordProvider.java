package org.probato.record;

import org.probato.model.Dimension;
import org.probato.model.Directory;
import org.probato.model.Target;
import org.probato.model.Video;
import org.probato.type.Screen;

/**
 * <p>
 * There must be at most one {@code RecordProvider} implementation
 * </p>
 */
public interface RecordProvider {

	/**
	 * Create a screen recording.
	 */
	ScreenRecorder createScreenRecord(Screen screen, Video video, Dimension dimension, String outputFile);

	/**
	 * Create a screenshot.
	 */
	Screenshot createScreenshot(Screen screen, Dimension dimension, String outputFile);

	/**
	 * Delete all files from current execution.
	 */
	void deleteExecutionData(Target target, Directory directory);

}