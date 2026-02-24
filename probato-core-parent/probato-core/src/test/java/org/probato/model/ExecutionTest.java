package org.probato.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.probato.type.Screen;

@DisplayName("UT - Execution")
class ExecutionTest {

	@Test
	@DisplayName("Should create constructor object successfully")
	void shouldCreateConstructorObjectSuccessfully() {

		var engine = "engine";
		var increment = 1L;
		var screen = Screen.PRIMARY;
		var target = new Target();
		var manager = new Manager();
		var delay = new Delay();
		var video = new Video();
		var directory = new Directory();

		var model = new Execution(engine, increment, screen, target, manager, delay, video, directory);

		assertAll("Validate value",
				() -> assertEquals(engine, model.getEngine()),
				() -> assertEquals(increment, model.getIncrement()),
				() -> assertEquals(screen, model.getScreen()),
				() -> assertEquals(target, model.getTarget()),
				() -> assertEquals(manager, model.getManager()),
				() -> assertEquals(delay, model.getDelay()),
				() -> assertEquals(video, model.getVideo()),
				() -> assertEquals(directory, model.getDirectory()));
	}

	@Test
	@DisplayName("Should create default constructor object successfully")
	void shouldCreateDefauldConstructorObjectSuccessfully() {

		var engine = "engine";
		var increment = 1L;
		var screen = Screen.PRIMARY;
		var target = new Target();
		var manager = new Manager();
		var delay = new Delay();
		var video = new Video();
		var directory = new Directory();

		var model = new Execution();
		model.setEngine(engine);
		model.setIncrement(increment);
		model.setScreen(screen);
		model.setTarget(target);
		model.setManager(manager);
		model.setDelay(delay);
		model.setVideo(video);
		model.setDirectory(directory);

		assertAll("Validate value",
				() -> assertEquals(engine, model.getEngine()),
				() -> assertEquals(increment, model.getIncrement()),
				() -> assertEquals(screen, model.getScreen()),
				() -> assertEquals(target, model.getTarget()),
				() -> assertEquals(manager, model.getManager()),
				() -> assertEquals(delay, model.getDelay()),
				() -> assertEquals(video, model.getVideo()),
				() -> assertEquals(directory, model.getDirectory()));
	}

	@Test
	@DisplayName("Should create build object successfully")
	void shouldCreateBuildObjectSuccessfully() {

		var engine = "engine";
		var increment = 1L;
		var screen = Screen.PRIMARY;
		var target = new Target();
		var manager = new Manager();
		var delay = new Delay();
		var video = new Video();
		var directory = new Directory();

		var model = Execution.builder()
				.engine(engine)
				.increment(increment)
				.screen(screen)
				.target(target)
				.manager(manager)
				.delay(delay)
				.video(video)
				.directory(directory)
				.build();

		assertAll("Validate value",
				() -> assertEquals(engine, model.getEngine()),
				() -> assertEquals(increment, model.getIncrement()),
				() -> assertEquals(screen, model.getScreen()),
				() -> assertEquals(target, model.getTarget()),
				() -> assertEquals(manager, model.getManager()),
				() -> assertEquals(delay, model.getDelay()),
				() -> assertEquals(video, model.getVideo()),
				() -> assertEquals(directory, model.getDirectory()));
	}

}