package org.probato.record;

import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.probato.model.Dimension;
import org.probato.model.Video;
import org.probato.type.DimensionMode;
import org.probato.type.Screen;

abstract class AbstractScreen {

	private static final String TESTANO_TEXT_SCREEN = "PROBATO.ORG";
	private static final Integer TEXT_SIZE = 16;
	private static final Integer WIDTH_TEXT_POSITION = 135;
	private static final Integer HEIGHT_TEXT_POSITION = 15;
	private static final Integer WIDTH_MIN_LENGTH = 400;
	private static final Integer HEIGHT_MIN_LENGTH = 300;

	protected Screen screen;
	protected Video video;
	protected Rectangle screenBounds;
	protected Integer width;
	protected Integer height;

	protected AbstractScreen(Screen screen, Video video, Dimension dimension) {
		this.screen = screen;
		this.video = video;
		setScreenBounds();
		setSize(dimension);
	}

	protected final BufferedImage addText(BufferedImage image) {

		var graphics = image.createGraphics();
		var textColor = Color.GRAY;
		graphics.setColor(textColor);
		graphics.setFont(new Font(null, Font.BOLD, TEXT_SIZE));
		graphics.drawString(TESTANO_TEXT_SCREEN, width - WIDTH_TEXT_POSITION, height - HEIGHT_TEXT_POSITION);

		return image;
	}

	protected Rectangle getRectangle() {
		return new Rectangle(this.screenBounds.x, this.screenBounds.y, this.width, this.height);
	}

	private void setSize(Dimension dimension) {

		int widthTemp;
		int heightTemp;
		if (DimensionMode.CUSTOM.equals(dimension.getMode())) {

			widthTemp = dimension.getWidth();
			heightTemp = dimension.getHeight();

		} else {

			widthTemp = screenBounds.width;
			heightTemp = screenBounds.height;
		}

		this.width = getWidth(widthTemp);
		this.height = getHeight(heightTemp);
	}

	private int getWidth(int width) {
		return (width > screenBounds.width) ? screenBounds.width : getMinWidth(width);
	}

	private int getHeight(int height) {
		return (height > screenBounds.height) ? screenBounds.height : getMinHeight(height);
	}

	private int getMinWidth(int width) {
		return (width < WIDTH_MIN_LENGTH) ? WIDTH_MIN_LENGTH : width;
	}

	private int getMinHeight(int height) {
		return (height < HEIGHT_MIN_LENGTH) ? HEIGHT_MIN_LENGTH : height;
	}

	private void setScreenBounds() {
		this.screenBounds = getScreen(screen);
	}

	protected static Rectangle getScreen(Screen screen) {

		Rectangle rectangle = null;

		var principalGe = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		var ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		var screens = Stream.of(ge.getScreenDevices())
				.filter(item -> !item.equals(principalGe))
				.collect(Collectors.toList());
		try {

			if (Screen.PRIMARY.equals(screen)) {
				rectangle = principalGe.getDefaultConfiguration().getBounds();
			} else if (!screens.isEmpty()) {
				rectangle = screens.get(0).getDefaultConfiguration().getBounds();
			}
		} catch (IndexOutOfBoundsException ex) {
			rectangle = principalGe.getDefaultConfiguration().getBounds();
		}

		return rectangle;
	}
}