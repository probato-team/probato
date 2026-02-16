package org.probato.record;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.probato.exception.ExecutionException;
import org.probato.model.Dimension;
import org.probato.type.Screen;

public final class Screenshot extends AbstractScreen {

	private static final String ERROR_DEFAULT_MSG = "An error occurred while trying to screenshot the execution: {0}";
	public static final String FORMAT_JPG = "JPG";

	private Robot robot;
	private String outputFile;

	public Screenshot(String outputFile, Screen screen, Dimension dimension) throws AWTException {
		super(screen, null, dimension);
		this.outputFile = outputFile;
		this.robot = new Robot();
	}

	public void print() {
		try {

			Rectangle rectangle = getRectangle();
			BufferedImage screen = robot.createScreenCapture(rectangle);
			BufferedImage bgrScreen = addText(screen);
			File file = new File(outputFile);
			ImageIO.write(bgrScreen, FORMAT_JPG, file);

		} catch (Exception ex) {
			throw new ExecutionException(ERROR_DEFAULT_MSG, ex.getMessage());
		}
	}
}