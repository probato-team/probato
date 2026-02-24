package org.probato.browser;

import org.openqa.selenium.WebDriver;
import org.probato.type.BrowserType;

public class FakeChromeBrowserProvider implements BrowserProvider {

	@Override
	public BrowserType getType() {
		return BrowserType.CHROME;
	}

	@Override
	public BrowserSession createSession(BrowserSessionData data) {
		return new FakeChromeSession();
	}

	/**
	 * Fake browser session.
	 */
	class FakeChromeSession implements NativeBrowserSession<WebDriver> {

		private WebDriver driver;

		@Override
		public WebDriver driver() {
			return driver;
		}

		@Override
		public String description() {
			return "Fake Chrome";
		}

		@Override
		public String version() {
			return "0.0.0";
		}

		@Override
		public void run() {
			// run
		}

		@Override
		public void destroy() {
			// destroy
		}

	}

}