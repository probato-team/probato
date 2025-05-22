package org.probato.browser;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.probato.entity.type.BrowserType;

public class EdgeBrowser extends BrowserServiceTemplate {

	private static final BrowserType TYPE = BrowserType.EDGE;
	private static final String HEADLESS_ARG = "--headless=new";
	
	@Override
	public boolean accepted(BrowserType type) {
		return TYPE.equals(type);
	}

	@Override
	protected WebDriver setup() {
		return new EdgeDriver(getOptions());
	}

	private EdgeOptions getOptions() {
		var options = new EdgeOptions();
		if (getBrowser().isHeadless()) {
			options.addArguments(HEADLESS_ARG);
		}
		return options;
	}
}