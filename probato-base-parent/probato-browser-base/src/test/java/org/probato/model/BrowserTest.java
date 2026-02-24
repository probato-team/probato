package org.probato.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.probato.type.BrowserType;

@DisplayName("UT - Browser")
class BrowserTest {

	@Test
	@DisplayName("Should create constructor object successfully")
	void shouldCreateConstructorObjectSuccessfully() {

		var browserType = BrowserType.CHROME;
		var isHeadless = Boolean.TRUE;
		var dimension = new Dimension();

		var model = new Browser(browserType, isHeadless, dimension);

		assertAll("Validate value",
				() -> assertEquals(browserType, model.getType()),
				() -> assertEquals(isHeadless, model.isHeadless()),
				() -> assertEquals(dimension, model.getDimension()));
	}

	@Test
	@DisplayName("Should create default constructor object successfully")
	void shouldCreateDefauldConstructorObjectSuccessfully() {

		var browserType = BrowserType.CHROME;
		var isHeadless = Boolean.TRUE;
		var dimension = new Dimension();

		var model = new Browser();
		model.setHeadless(isHeadless);
		model.setType(browserType);
		model.setDimension(dimension);

		assertAll("Validate value",
				() -> assertEquals(browserType, model.getType()),
				() -> assertEquals(isHeadless, model.isHeadless()),
				() -> assertEquals(dimension, model.getDimension()));
	}

	@Test
	@DisplayName("Should create build object successfully")
	void shouldCreateBuildObjectSuccessfully() {

		var browserType = BrowserType.CHROME;
		var isHeadless = Boolean.TRUE;
		var dimension = new Dimension();

		var model = Browser.builder()
				.headless(isHeadless)
				.type(browserType)
				.dimension(dimension)
				.build();

		assertAll("Validate value",
				() -> assertEquals(browserType, model.getType()),
				() -> assertEquals(isHeadless, model.isHeadless()),
				() -> assertEquals(dimension, model.getDimension()));
	}

}