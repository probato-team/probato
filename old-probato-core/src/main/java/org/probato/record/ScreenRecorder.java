package org.probato.record;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.probato.exception.ExecutionException;
import org.probato.model.Dimension;
import org.probato.model.Video;
import org.probato.model.type.Screen;

import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.xuggler.ICodec;

public final class ScreenRecorder extends AbstractScreen implements Runnable {

	private static final String ERROR_DEFAULT_MSG = "An error occurred while trying to record screen the execution: {0}";

	private double frameRate;
	private Robot robot;
	private IMediaWriter writer;
	private ScheduledExecutorService pool;
	private long startTime;

	public ScreenRecorder(String outputFile, Screen screen, Video video, Dimension dimension) throws AWTException {
		super(screen, dimension);
		this.frameRate = video.getFrameRate();
		this.robot = new Robot();
		this.writer = ToolFactory.makeWriter(outputFile);
		this.screenBounds = getScreen(screen);
		setVideoStream(video);
	}

	public void startCapture() {
		try {

			this.startTime = System.nanoTime();
			this.pool.scheduleAtFixedRate(this, 0L, (long) (1000.0 / this.frameRate), TimeUnit.MILLISECONDS);

		} catch (Exception ex) {
			Thread.currentThread().interrupt();
			throw new ExecutionException(ERROR_DEFAULT_MSG, ex.getMessage());
		}
	}

	public void stopCapture() {
		try {

			TimeUnit.SECONDS.sleep(1);
			this.pool.shutdown();
			this.pool.awaitTermination(1, TimeUnit.SECONDS);
			this.writer.close();

		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
			throw new ExecutionException(ERROR_DEFAULT_MSG, ex.getMessage());
		}
	}

	@Override
	public void run() {
		var rectangle = getRectangle();
		var screen = this.robot.createScreenCapture(rectangle);
		var bgrScreen = convertToType(screen, BufferedImage.TYPE_3BYTE_BGR);
		this.writer.encodeVideo(0, bgrScreen, System.nanoTime() - this.startTime, TimeUnit.NANOSECONDS);
	}

	private BufferedImage convertToType(BufferedImage sourceImage, int targetType) {
		var image = new BufferedImage(sourceImage.getWidth(), sourceImage.getHeight(), targetType);
		image.getGraphics().drawImage(sourceImage, 0, 0, null);
		return addText(image);
	}

	private void setVideoStream(Video video) {

		var quality = video.getQuality();
		int widthTemp = this.width / quality.getDivisor();
		if (isOdd(widthTemp)) {
			widthTemp++;
		}

		int heightTemp = this.height / quality.getDivisor();
		if (isOdd(heightTemp)) {
			heightTemp++;
		}

		this.writer.addVideoStream(0, 0, ICodec.ID.CODEC_ID_H264, widthTemp, heightTemp);
		this.pool = Executors.newScheduledThreadPool(1);
	}

	private boolean isOdd(int side) {
		return side % 2 != 0;
	}

}