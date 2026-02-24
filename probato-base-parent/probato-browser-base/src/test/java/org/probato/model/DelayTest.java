package org.probato.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("UT - Delay")
class DelayTest {

	@Test
	@DisplayName("Should create constructor object successfully")
	void shouldCreateConstructorObjectSuccessfully() {

		var waiting = 30_000;
		var actionInterval = 1_00;

		var model = new Delay(waiting, actionInterval);

		assertAll("Validate value",
				() -> assertEquals(waiting, model.getWaitingTimeout()),
				() -> assertEquals(actionInterval, model.getActionInterval()));
	}

	@Test
	@DisplayName("Should create default constructor object successfully")
	void shouldCreateDefauldConstructorObjectSuccessfully() {

		var waiting = 30_000;
		var actionInterval = 1_00;

		var model = new Delay();
		model.setWaitingTimeout(waiting);
		model.setActionInterval(actionInterval);

		assertAll("Validate value",
				() -> assertEquals(waiting, model.getWaitingTimeout()),
				() -> assertEquals(actionInterval, model.getActionInterval()));
	}

	@Test
	@DisplayName("Should create build object successfully")
	void shouldCreateBuildObjectSuccessfully() {

		var waiting = 30_000;
		var actionInterval = 1_00;

		var model = Delay.builder()
				.waitingTimeout(waiting)
				.actionInterval(actionInterval)
				.build();

		assertAll("Validate value",
				() -> assertEquals(waiting, model.getWaitingTimeout()),
				() -> assertEquals(actionInterval, model.getActionInterval()));
	}

}