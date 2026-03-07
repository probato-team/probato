package org.probato.browser;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.probato.type.DimensionMode;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

/**
 * Playwright-based Edge browser session.
 */
final class PlaywrightEdgeSession implements NativeBrowserSession<Page> {

	private final BrowserSessionData data;

	private Page driver;
	private Playwright playwright;
	private Browser browserInstance;
	private BrowserContext context;

	public PlaywrightEdgeSession(BrowserSessionData data) {
		this.data = data;
	}

	@Override
	public Page driver() {
		return driver;
	}

	@Override
	public void run() {

		if (Objects.nonNull(driver)) {
			return;
		}

		setup();
		configure();
	}

	@Override
	public void destroy() {

		if (context != null) {
			context.close();
			context = null;
		}

		if (browserInstance != null) {
			browserInstance.close();
			browserInstance = null;
		}

		if (playwright != null) {
			playwright.close();
			playwright = null;
		}

		driver = null;
	}

	@Override
	public String description() {

		if (Objects.isNull(driver)) {
			return "Microsoft Edge (not started)";
		}

		var browserName = "Microsoft Edge";
		var dimensionMode = data.getBrowser().getDimension().getMode().description();
		var size = driver.viewportSize();

		var windowSize = getWindowSize();
		var innerWidth  = ((Number) windowSize.get("width"));
		var innerHeight = ((Number) windowSize.get("height"));

		var width = Optional
				.ofNullable(size)
				.map(item -> item.width)
				.map(String::valueOf)
				.orElse(innerWidth.toString());

		var height = Optional
				.ofNullable(size)
				.map(item -> item.height)
				.map(String::valueOf)
				.orElse(innerHeight.toString());

		return MessageFormat.format("{0}-{1}({2}x{3})", browserName, dimensionMode, width, height);
	}

	@Override
	public String version() {
		return Objects.nonNull(browserInstance)
				? browserInstance.version()
				: "unknown";
	}

	private void setup() {

		playwright = Playwright.create();

		var args = configureBrowser();
		browserInstance = playwright.
				chromium()
				.launch(new BrowserType.LaunchOptions()
						.setChannel("msedge")
						.setHeadless(data.getBrowser().isHeadless())
						.setArgs(args));

		var contextOptions = new Browser.NewContextOptions();
		if (args.isEmpty()) {

			contextOptions.setViewportSize(
					data.getBrowser().getDimension().getWidth(),
					data.getBrowser().getDimension().getHeight());

		} else {
			contextOptions.setViewportSize(null);
		}

		context = browserInstance.newContext(contextOptions);
		context.setDefaultTimeout(data.getDelay().getActionInterval());
		context.setDefaultNavigationTimeout(data.getDelay().getWaitingTimeout());

		driver = context.newPage();
	}

	private void configure() {
		configureUrl();
	}

	private void configureUrl() {
		driver.navigate(data.getUrl());
	}

	private List<String> configureBrowser() {

		var args = new ArrayList<String>();
		if (shouldStartMaximized()) {
			args.add("--start-maximized");
		}

		return args;
	}

	private boolean shouldStartMaximized() {
		return data.getBrowser().getDimension().getMode() != DimensionMode.CUSTOM;
	}

	@SuppressWarnings("unchecked")
	private Map<String, Object> getWindowSize() {
		return (Map<String, Object>) driver.evaluate("() => ({ width: window.innerWidth, height: window.innerHeight })");
	}

}