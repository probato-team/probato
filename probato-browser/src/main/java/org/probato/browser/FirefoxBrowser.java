package org.probato.browser;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.probato.entity.type.BrowserType;

public class FirefoxBrowser extends BrowserServiceTemplate {

	private static final BrowserType TYPE = BrowserType.FIREFOX;
	private static final String HEADLESS_ARG = "--headless";
	
	@Override
	public boolean accepted(BrowserType type) {
		return TYPE.equals(type);
	}

	@Override
	protected WebDriver setup() {
		return new FirefoxDriver(getOptions());
	}

	private FirefoxOptions getOptions() {
		var options = new FirefoxOptions();
		if (getBrowser().isHeadless()) {
			options.addArguments(HEADLESS_ARG);
		}
		return options;
	}
}