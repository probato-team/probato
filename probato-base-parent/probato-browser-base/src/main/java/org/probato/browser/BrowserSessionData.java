package org.probato.browser;

import org.probato.model.Browser;
import org.probato.model.Delay;
import org.probato.type.Screen;

public class BrowserSessionData {

	private final Browser browser;
	private final String engine;
	private final String url;
	private final Screen screen;
	private final Delay delay;

	public BrowserSessionData(Browser browser, String engine, String url, Screen screen, Delay delay) {
		this.browser = browser;
		this.engine = engine;
		this.url = url;
		this.screen = screen;
		this.delay = delay;
	}

	public Browser getBrowser() {
		return browser;
	}

	public String getEngine() {
		return engine;
	}

	public String getUrl() {
		return url;
	}

	public Screen getScreen() {
		return screen;
	}

	public Delay getDelay() {
		return delay;
	}

}