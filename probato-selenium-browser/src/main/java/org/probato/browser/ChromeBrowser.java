package org.probato.browser;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.probato.entity.type.BrowserType;

public class ChromeBrowser extends BrowserServiceTemplate {

	private static final BrowserType TYPE = BrowserType.CHROME;
	private static final String HEADLESS_ARG = "--headless=new";

	@Override
	public boolean accepted(BrowserType type) {
		return TYPE.equals(type);
	}

	@Override
	protected WebDriver setup() {
		return new ChromeDriver(getOptions());
	}

	private ChromeOptions getOptions() {
		var options = new ChromeOptions();
		if (getBrowser().isHeadless()) {
			options.addArguments(HEADLESS_ARG);
		}
		return options;
	}
}