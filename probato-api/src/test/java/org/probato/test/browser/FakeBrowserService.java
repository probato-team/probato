package org.probato.test.browser;

import org.probato.browser.BrowserService;
import org.probato.entity.model.Browser;
import org.probato.entity.type.BrowserType;

public class FakeBrowserService implements BrowserService {

	private static boolean accept = Boolean.TRUE;
	
	@Override
	public Object driver() {
		return null;
	}

	@Override
	public String getBrowserDescription() {
		return "fake";
	}

	@Override
	public String getBrowserVersion() {
		return "v.0.0.0";
	}

	@Override
	public void run() {
		// Implementation run
	}

	@Override
	public void destroy() {
		// Implementation destroy
	}

	@Override
	public boolean accepted(BrowserType type) {
		return FakeBrowserService.accept;
	}

	@Override
	public void browser(Browser browser) {
		// Implementation browser
	}

	public static void setAccept(boolean accept) {
		FakeBrowserService.accept = accept;
	}
}