package org.probato.browser;

import org.probato.model.Browser;
import org.probato.model.Delay;
import org.probato.type.Screen;

public class BrowserSessionData {

	private final String engine;
	private final String url;
	private final Screen screen;
	private final Browser browser;
	private final Delay delay;

	public BrowserSessionData(String engine, String url, Screen screen, Browser browser, Delay delay) {
		super();
		this.engine = engine;
		this.url = url;
		this.screen = screen;
		this.browser = browser;
		this.delay = delay;
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

	public Browser getBrowser() {
		return browser;
	}

	public Delay getDelay() {
		return delay;
	}

}