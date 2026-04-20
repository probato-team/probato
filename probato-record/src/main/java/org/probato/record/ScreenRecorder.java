package org.probato.record;

import static org.bytedeco.ffmpeg.global.avutil.AV_LOG_QUIET;
import static org.bytedeco.ffmpeg.global.avutil.AV_PIX_FMT_YUV420P;
import static org.bytedeco.ffmpeg.global.avutil.av_log_set_level;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.probato.exception.ExecutionException;
import org.probato.model.Dimension;
import org.probato.model.Video;
import org.probato.type.Screen;

public final class ScreenRecorder extends AbstractScreen implements Runnable {

	private static final String ERROR_DEFAULT_MSG = "An error occurred while trying to record screen the execution: {0}";

	private long frameIndex = 0;
	private boolean recording;
	private Robot robot;
	private ScheduledExecutorService pool;
	private FFmpegFrameRecorder recorder;
	private Java2DFrameConverter converter;

	public ScreenRecorder(String outputFile, Screen screen, Video video, Dimension dimension) throws AWTException {

		super(screen, video, dimension);

		av_log_set_level(AV_LOG_QUIET);

		this.robot = new Robot();
		this.converter = new Java2DFrameConverter();
		this.width = (this.width % 2 == 0) ? this.width : this.width + 1;
		this.height = (this.height % 2 == 0) ? this.height : this.height + 1;
		this.recorder = new FFmpegFrameRecorder(outputFile, this.width, this.height);
	}

	public void startCapture() {
		try {

			if (!video.isEnabled() || recording) return;

			configureRecorder();

			this.pool = Executors.newScheduledThreadPool(1);
			var period = (long) (1000.0 / video.getFrameRate());
			this.pool.scheduleAtFixedRate(this, 0, period, TimeUnit.MILLISECONDS);

			this.recorder.start();
			this.recording = true;

		} catch (Exception ex) {
			Thread.currentThread().interrupt();
			throw new ExecutionException(ERROR_DEFAULT_MSG, ex.getMessage());
		}
	}

	public void stopCapture() {
		try {

			if (!video.isEnabled() || !recording) return;

			TimeUnit.SECONDS.sleep(1);
			this.pool.shutdown();
			this.pool.awaitTermination(2, TimeUnit.SECONDS);

			recorder.stop();
			recorder.release();

			this.recording = false;

		} catch (Exception ex) {
			Thread.currentThread().interrupt();
			throw new ExecutionException(ERROR_DEFAULT_MSG, ex.getMessage());
		}
	}

	@Override
	public void run() {
		try {

			var screen = robot.createScreenCapture(getRectangle());
			var image = new BufferedImage(
					screen.getWidth(),
					screen.getHeight(),
					BufferedImage.TYPE_3BYTE_BGR);

			image.getGraphics().drawImage(screen, 0, 0, null);

			var imageWithText = addText(image);
			var frame = converter.convert(imageWithText);

			var timestamp = (long) (frameIndex * (1_000_000.0 / video.getFrameRate()));
			recorder.setTimestamp(timestamp);
			recorder.record(frame);

			frameIndex++;

		} catch (Exception ex) {
			Thread.currentThread().interrupt();
			throw new ExecutionException(ERROR_DEFAULT_MSG, ex.getMessage());
		}
	}

	private void configureRecorder() {
		recorder.setFormat("mp4");
		recorder.setVideoCodecName("libx264");
		recorder.setPixelFormat(AV_PIX_FMT_YUV420P);
		recorder.setVideoOption("preset", "ultrafast");
		recorder.setVideoOption("tune", "zerolatency");
		recorder.setVideoOption("crf", "23");
		recorder.setVideoBitrate(2_000_000);
		recorder.setFrameRate(video.getFrameRate());
		recorder.setGopSize((int) video.getFrameRate());
	}

}