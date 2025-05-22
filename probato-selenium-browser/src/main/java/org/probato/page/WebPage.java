package org.probato.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.probato.entity.model.PageObject;

public abstract class WebPage implements PageObject {

	private WebDriver driver;

	public void setDriver(Object driver) {
		this.driver = (WebDriver) driver;
		setup();
	}

	public WebDriver driver() {
		return driver;
	}

	private void setup() {
		PageFactory.initElements(driver(), this);
	}

}