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
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.probato.type.Screen;

/**
 * Selenium-based Firefox browser session.
 */
final class SeleniumFirefoxSession implements NativeBrowserSession<WebDriver> {

	private final BrowserSessionData data;

	private WebDriver driver;

	public SeleniumFirefoxSession(BrowserSessionData data) {
		this.data = data;
	}

	@Override
	public WebDriver driver() {
		return driver;
	}

	@Override
	public String description() {

		if (Objects.isNull(driver)) {
			return "Mozilla Firefox (not started)";
		}

		var browserName = "Mozilla Firefox";
		var dimensionMode = data.getBrowser().getDimension().getMode().description();
		var size = driver.manage().window().getSize();

		var width = Optional
				.ofNullable(size.getWidth())
				.map(String::valueOf)
				.orElse("");

		var height = Optional
				.ofNullable(size.getHeight())
				.map(String::valueOf)
				.orElse("");

		return MessageFormat.format("{0}-{1}({2}x{3})", browserName, dimensionMode, width, height);
	}

	@Override
	public String version() {
		if (driver instanceof RemoteWebDriver) {
			return ((RemoteWebDriver) driver).getCapabilities().getBrowserVersion();
		}
		return "unknown";
	}

	@Override
	public void run() {

		if (Objects.nonNull(driver)) {
	        return;
	    }

		driver = setup();
		configure();
	}

	@Override
	public void destroy() {
		if (Objects.nonNull(driver)) {
			driver.quit();
			driver = null;
		}
	}

	private WebDriver setup() {
		return new FirefoxDriver(getOptions());
	}

	private FirefoxOptions getOptions() {
		var options = new FirefoxOptions();
		if (data.getBrowser().isHeadless()) {
			options.addArguments("--headless");
		}
		return options;
	}

	private void configure() {
		configureUrl();
		configureTimeouts();
		configurePositionOnScreen();
		configureResize();
	}

	private void configureUrl() {
		driver.get(data.getUrl());
	}

	private void configureTimeouts() {
		driver
			.manage()
			.timeouts()
			.implicitlyWait(Duration.ofMillis(data.getDelay().getWaitingTimeout()));
	}

	private void configurePositionOnScreen() {
		driver
			.manage()
			.window()
			.setPosition(getPoint());
	}

	private void configureResize() {
		switch (data.getBrowser().getDimension().getMode()) {

			case CUSTOM:

				driver()
					.manage()
					.window()
					.setSize(new Dimension(
							data.getBrowser().getDimension().getWidth(),
							data.getBrowser().getDimension().getHeight()));

				break;

			case MAXIMIZED:

				driver()
					.manage()
					.window()
					.maximize();

				break;

			case FULLSCREEN:
			default:

				driver()
					.manage()
					.window()
					.fullscreen();
		}
	}

	private Point getPoint() {

		var x = 0;
		var y = 0;
		var screenBounds = getScreenBounds(data.getScreen());
		if (Objects.nonNull(screenBounds)) {
			if (isSecondary(data.getScreen())) {

				var screenBoundsPrincipal = getScreenBounds(Screen.PRIMARY);
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

	private Rectangle getScreenBounds(Screen screen) {

        Rectangle result = null;

        var principalGe = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        var ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        var screens = Stream
        		.of(ge.getScreenDevices())
        		.filter(item -> !item.equals(principalGe))
        		.collect(Collectors.toList());

        try {

        	if (Screen.PRIMARY.equals(screen)) {
        		result = principalGe
        				.getDefaultConfiguration()
        				.getBounds();
        	} else if (!screens.isEmpty()) {
                result = screens
                		.get(0)
                		.getDefaultConfiguration()
                		.getBounds();
            }

        } catch (IndexOutOfBoundsException ex) {
        	result = principalGe.getDefaultConfiguration().getBounds();
        }

        return result;
    }

	private boolean isSecondary(Screen screen) {
	    return !Screen.PRIMARY.equals(screen);
	}

}