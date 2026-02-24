package org.probato.engine.builder;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.Instant;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("UT - Step")
class StepTest {

	@Test
	@DisplayName("Should create object successfully")
	void shouldCreateObjectSuccessfully() {

		var sequence = 0;
		var text = "text";
		var start = Instant.now();
		var end = Instant.now();
		var clazz = "clazz";
		var method = "method";
		var error = "error";

		var model = Step.builder()
				.sequence(sequence)
				.text(text)
				.start(start)
				.end(end)
				.clazz(clazz)
				.method(method)
				.error(error)
				.build();

		assertAll("Validate value",
				() -> assertEquals(sequence, model.getSequence()),
				() -> assertEquals(text, model.getText()),
				() -> assertNotNull(model.getRuntime()),
				() -> assertEquals(clazz, model.getClazz()),
				() -> assertEquals(method, model.getMethod()),
				() -> assertEquals(error, model.getError()));
	}

}