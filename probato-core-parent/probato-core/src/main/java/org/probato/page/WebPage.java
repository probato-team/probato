package org.probato.page;

import java.util.Objects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.probato.model.PageObject;

public abstract class WebPage implements PageObject {

	private WebDriver driver;

	public final void setDriver(Object driver) {
		this.driver = (WebDriver) driver;
		setup();
	}

	public final WebDriver driver() {
		return driver;
	}

	private final void setup() {
		if (Objects.nonNull(driver())) {
			PageFactory.initElements(driver(), this);
		}
	}

}