package org.probato.record;

import java.util.Optional;

import org.probato.core.loader.Configuration;
import org.probato.entity.model.Dimension;
import org.probato.exception.ExecutionException;

public class RecordServiceImpl implements RecordService {
	
	private static final String ERROR_DEFAULT_MSG = "An error occurred while trying to record screen the execution: {0}";
	
	private ScreenRecorder recorder;
	
	@Override
	public void start(String outputFile, Dimension dimension) {
		try {
			
			var configuration = Configuration.getInstance();
			var screen = configuration.getExecution().getScreen();
			var video = configuration.getExecution().getVideo();
			
			if (video.isEnabled()) {
				recorder = new ScreenRecorder(outputFile, screen, video, dimension);
				recorder.startCapture();
			}

		} catch (Exception ex) {
			Thread.currentThread().interrupt();
			throw new ExecutionException(ERROR_DEFAULT_MSG, ex.getMessage());
		}
	}

	@Override
	public void stop() {
		
		var configuration = Configuration.getInstance();
		var video = configuration.getExecution().getVideo();
		if (video.isEnabled()) {
			
			Optional.ofNullable(recorder)
				.ifPresent(ScreenRecorder::stopCapture);
		}
	}

	@Override
	public void screenshot(String outputFile, Dimension dimension) {
		try {
			
			var configuration = Configuration.getInstance();
			var screen = configuration.getExecution().getScreen();
			
			new Screenshot(outputFile, screen, dimension).print();

		} catch (Exception ex) {
			throw new ExecutionException(ERROR_DEFAULT_MSG, ex.getMessage());
		}
	}
	
}