package org.probato.browser;

import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.text.MessageFormat;
import java.time.Duration;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.probato.loader.Configuration;
import org.probato.model.Browser;
import org.probato.model.type.Screen;

abstract class BrowserServiceTemplate implements BrowserService {

	protected WebDriver driver;
	private Browser browser;
	
	protected BrowserServiceTemplate() {}

	@Override
	public final void run() {
		driver = setup();
		setConfiguration();
	}

	@Override
	public final void destroy() {
		driver.quit();
	}

	@Override
	public void browser(Browser browser) {
		this.browser = browser;
	}

	@Override
	public final WebDriver driver() {
		return driver;
	}

	@Override
	public String getBrowserDescription() {

		var browserMode = browser.getDimension().getMode();
		var size = driver.manage().window().getSize();
		var width = Optional.ofNullable(size.getWidth()).map(value -> value + "").orElse("");
		var height = Optional.ofNullable(size.getHeight()).map(value -> value + "").orElse("");
		var navigatiorName = browser.getType().descritpion();

		return getBrowserDescription(navigatiorName, browserMode.description(), width, height);
	}

	@Override
	public String getBrowserVersion() {
		var cap = ((RemoteWebDriver) driver).getCapabilities();
		return cap.getBrowserVersion();
	}

	protected abstract WebDriver setup();

	protected Browser getBrowser() {
		return browser;
	}

	protected String getUrl() {
		return Configuration.getInstance().getExecution().getTarget().getUrl();
	}

	private void setConfiguration() {
		setUrl();
		setTimeouts();
		setPositionScreen();
		setDimensionMode();
	}

	private void setUrl() {
		driver.get(getUrl());
	}

	private void setTimeouts() {
		driver.manage().timeouts()
				.implicitlyWait(Duration.ofMillis(Configuration.getInstance().getExecution().getDelay().getWaitingTimeout()));
	}

	private void setPositionScreen() {
		driver.manage().window().setPosition(getPoint());
	}

	private void setDimensionMode() {
		var mode = browser.getDimension().getMode();
		switch (mode) {
			case CUSTOM:
				
				var widht = browser.getDimension().getWidth();
				var height = browser.getDimension().getHeight();
				var dimensionSize = new Dimension(widht, height);

				driver().manage().window().setSize(dimensionSize);
				break;
				
			case MAXIMIZED:
				
				driver().manage().window().maximize();
				break;
				
			case FULLSCREEN:
			default:
				
				driver().manage().window().fullscreen();
		}
	}
	
	private Point getPoint() {

		var x = 0;
		var y = 0;
		var screenBounds = getScreen(Configuration.getInstance().getExecution().getScreen());
		if (Objects.nonNull(screenBounds)) {
			if (!Screen.PRINCIPAL.equals(Configuration.getInstance().getExecution().getScreen())) {
				
				Rectangle screenBoundsPrincipal = getScreen(Screen.PRINCIPAL);
				if (Objects.nonNull(screenBoundsPrincipal)) {
					x = screenBoundsPrincipal.width;
					y = screenBoundsPrincipal.height - screenBounds.height;
				}
				
				if (screenBounds.x < 0 || screenBounds.y <0) {
					x = screenBounds.x;
					y = screenBounds.y;
				}

			} else {
				x = screenBounds.x;
				y = screenBounds.y;
			}
		}

		return new Point(x, y);
	}
	
	private static Rectangle getScreen(Screen screen) {

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

	private String getBrowserDescription(String name, String dimension, String width, String height) {
		return MessageFormat.format("{0} - {1}({2}x{3})", name, dimension, width, height);
	}

}