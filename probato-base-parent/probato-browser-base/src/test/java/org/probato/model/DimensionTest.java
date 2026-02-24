package org.probato.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.probato.type.DimensionMode;

@DisplayName("UT - Dimension")
class DimensionTest {

	@Test
	@DisplayName("Should create constructor object successfully")
	void shouldCreateConstructorObjectSuccessfully() {

		var width = 1200;
		var height = 800;
		var mode = DimensionMode.MAXIMIZED;

		var model = new Dimension(width, height, mode);

		assertAll("Validate value",
				() -> assertEquals(width, model.getWidth()),
				() -> assertEquals(height, model.getHeight()),
				() -> assertEquals(mode, model.getMode()));
	}

	@Test
	@DisplayName("Should create default constructor object successfully")
	void shouldCreateDefauldConstructorObjectSuccessfully() {

		var width = 1200;
		var height = 800;
		var mode = DimensionMode.MAXIMIZED;

		var model = new Dimension();
		model.setWidth(width);
		model.setHeight(height);
		model.setMode(mode);

		assertAll("Validate value",
				() -> assertEquals(width, model.getWidth()),
				() -> assertEquals(height, model.getHeight()),
				() -> assertEquals(mode, model.getMode()));
	}

	@Test
	@DisplayName("Should create build object successfully")
	void shouldCreateBuildObjectSuccessfully() {

		var width = 1200;
		var height = 800;
		var mode = DimensionMode.MAXIMIZED;

		var model = Dimension.builder()
				.width(width)
				.height(height)
				.mode(mode)
				.build();

		assertAll("Validate value",
				() -> assertEquals(width, model.getWidth()),
				() -> assertEquals(height, model.getHeight()),
				() -> assertEquals(mode, model.getMode()));
	}

}