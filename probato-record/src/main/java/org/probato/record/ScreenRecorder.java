package org.probato.record;

import static org.bytedeco.ffmpeg.global.avutil.AV_LOG_QUIET;
import static org.bytedeco.ffmpeg.global.avutil.AV_PIX_FMT_YUV420P;
import static org.bytedeco.ffmpeg.global.avutil.av_log_set_level;

import java.awt.AWTException;
import java.awt.RenderingHints;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.probato.exception.ExecutionException;
import org.probato.model.Dimension;
import org.probato.model.Video;
import org.probato.type.Screen;

public final class ScreenRecorder extends AbstractScreen implements Runnable {

	private static final String ERROR_DEFAULT_MSG = "An error occurred while trying to record screen the execution: {0}";
	private static final int SHUTDOWN_TIMEOUT_SECONDS = 15;

	private final Robot robot;
	private final Java2DFrameConverter converter;
	private final FFmpegFrameRecorder recorder;
	private final AtomicBoolean processing = new AtomicBoolean(false);

	private volatile boolean recording;

	private BufferedImage frameBuffer;
	private ScheduledExecutorService scheduler;

	public ScreenRecorder(String outputFile, Screen screen, Video video, Dimension dimension) throws AWTException {

		super(screen, video, dimension);

		try {

			av_log_set_level(AV_LOG_QUIET);

			var outputPath = Path.of(outputFile).toAbsolutePath();

			this.width = makeEven(this.width);
			this.height = makeEven(this.height);

			this.robot = new Robot();
			this.converter = new Java2DFrameConverter();

			this.frameBuffer = new BufferedImage(
					this.width,
					this.height,
					BufferedImage.TYPE_3BYTE_BGR);

			this.recorder = new FFmpegFrameRecorder(
					outputPath.toString(),
					this.width,
					this.height);

			configureRecorder();

		} catch (Exception ex) {
			throw new ExecutionException(ERROR_DEFAULT_MSG, ex.getMessage());
		}
	}

	public void startCapture() {
		try {

			if (!video.isEnabled() || recording) {
				return;
			}

			recorder.start();
			recording = true;

			var periodMs = Math.max(
					1L,
					Math.round(1000.0 / video.getFrameRate()));

			scheduler = Executors.newSingleThreadScheduledExecutor();
			scheduler.scheduleAtFixedRate(
					this,
					0,
					periodMs,
					TimeUnit.MILLISECONDS);

			Thread.sleep(video.getStartDelay());

		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
			throw new ExecutionException(ERROR_DEFAULT_MSG, ex.getMessage());
		} catch (Exception ex) {
			throw new ExecutionException(ERROR_DEFAULT_MSG, ex.getMessage());
		}
	}

	public void stopCapture() {
		try {

			if (!video.isEnabled() || !recording) {
				return;
			}

			Thread.sleep(video.getStopDelay());
			recording = false;

			if (Objects.nonNull(scheduler)) {
				scheduler.shutdown();
				scheduler.awaitTermination(
						SHUTDOWN_TIMEOUT_SECONDS,
						TimeUnit.SECONDS);
			}

			while (processing.get()) {
				Thread.sleep(20);
			}

			recorder.stop();
			recorder.release();
			converter.close();

		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
			throw new ExecutionException(ERROR_DEFAULT_MSG, ex.getMessage());
		} catch (Exception ex) {
			throw new ExecutionException(ERROR_DEFAULT_MSG, ex.getMessage());
		}
	}

	@Override
	public void run() {

		if (!recording) {
			return;
		}

		if (!processing.compareAndSet(false, true)) {
			return;
		}

		try {

			var capture = robot.createScreenCapture(getRectangle());
			var graphics = frameBuffer.createGraphics();

			graphics.setRenderingHint(
					RenderingHints.KEY_INTERPOLATION,
					RenderingHints.VALUE_INTERPOLATION_BILINEAR);

			graphics.drawImage(capture, 0, 0, width, height, null);
			graphics.dispose();

			var finalImage = addText(frameBuffer);
			var frame = converter.convert(finalImage);

			recorder.record(frame);

		} catch (Exception ex) {
			throw new ExecutionException(ERROR_DEFAULT_MSG, ex.getMessage());
		} finally {
			processing.set(false);
		}
	}

	private void configureRecorder() {

		recorder.setFormat("mp4");
		recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);
		recorder.setPixelFormat(AV_PIX_FMT_YUV420P);
		recorder.setFrameRate(video.getFrameRate());
		recorder.setVideoBitrate(video.getBitrate());
		recorder.setGopSize((int) video.getFrameRate() * 2);
		recorder.setVideoOption("preset", "veryfast");
		recorder.setVideoOption("crf", "22");
		recorder.setVideoOption("movflags", "+faststart");
		recorder.setVideoOption("pix_fmt", "yuv420p");
	}

	private int makeEven(int value) {
		return (value % 2 == 0)
				? value
				: value + 1;
	}

}