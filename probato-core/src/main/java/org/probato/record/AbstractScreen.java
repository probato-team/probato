package org.probato.record;

import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.probato.entity.model.Dimension;
import org.probato.entity.type.DimensionMode;
import org.probato.entity.type.Screen;

abstract class AbstractScreen {

	private static final String TESTANO_TEXT_SCREEN = "TESTANO.COM";
	private static final Integer TEXT_SIZE = 16;
	private static final Integer WIDTH_TEXT_POSITION = 135;
	private static final Integer HEIGHT_TEXT_POSITION = 15;
	private static final Integer WIDTH_MIN_LENGTH = 400;
	private static final Integer HEIGHT_MIN_LENGTH = 300;

	protected Screen screen;
	protected Rectangle screenBounds;
	protected Integer width;
	protected Integer height;

	protected AbstractScreen(Screen screen, Dimension dimension) {
		this.screen = screen;
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

		Rectangle result = null;
	        
        var principalGe = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        var ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        var screens = Stream.of(ge.getScreenDevices())
        		.filter(item -> !item.equals(principalGe))
        		.collect(Collectors.toList());
        try {
        	
        	if (Screen.PRINCIPAL.equals(screen)) {
        		result = principalGe.getDefaultConfiguration().getBounds();
        	} else if (!screens.isEmpty()) {
                result = screens.get(0).getDefaultConfiguration().getBounds();
            }
        } catch (IndexOutOfBoundsException ex) {
        	result = principalGe.getDefaultConfiguration().getBounds();
        }

        return result;
    }
}