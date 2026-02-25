package org.probato.model;

import org.probato.type.Screen;

public class Execution {

	private String engine;
	private Long increment;
	private Screen screen = Screen.PRIMARY;
	private Target target;
	private Manager manager;
	private Delay delay;
	private Video video;
	private Directory directory;

	public Execution() {}

	public Execution(
			String engine,
			Long increment,
			Screen screen,
			Target target,
			Manager manager,
			Delay delay,
			Video video) {

		this();
		this.engine = engine;
		this.increment = increment;
		this.screen = screen;
		this.target = target;
		this.manager = manager;
		this.delay = delay;
		this.video = video;
		this.directory = new Directory();
	}

	public Execution(ExecutionBuilder builder) {
		this();
		this.engine = builder.engine;
		this.increment = builder.increment;
		this.screen = builder.screen;
		this.target = builder.target;
		this.manager = builder.manager;
		this.delay = builder.delay;
		this.video = builder.video;
		this.directory = new Directory();
	}

	public String getEngine() {
		return engine;
	}

	public void setEngine(String engine) {
		this.engine = engine;
	}

	public Long getIncrement() {
		return increment;
	}

	public void setIncrement(Long increment) {
		this.increment = increment;
	}

	public Screen getScreen() {
		return screen;
	}

	public void setScreen(Screen screen) {
		this.screen = screen;
	}

	public Target getTarget() {
		return target;
	}

	public void setTarget(Target target) {
		this.target = target;
	}

	public Manager getManager() {
		return manager;
	}

	public void setManager(Manager manager) {
		this.manager = manager;
	}

	public Delay getDelay() {
		return delay;
	}

	public void setDelay(Delay delay) {
		this.delay = delay;
	}

	public Video getVideo() {
		return video;
	}

	public void setVideo(Video video) {
		this.video = video;
	}

	public Directory getDirectory() {
		return directory;
	}

	public void setDirectory(Directory directory) {
		this.directory = directory;
	}

	public static ExecutionBuilder builder() {
		return new ExecutionBuilder();
	}

	public static class ExecutionBuilder {

		private String engine;
		private Long increment;
		private Screen screen = Screen.PRIMARY;
		private Target target;
		private Manager manager;
		private Delay delay;
		private Video video;

		private ExecutionBuilder() {}

		public ExecutionBuilder increment(Long increment) {
			this.increment = increment;
			return this;
		}

		public ExecutionBuilder engine(String engine) {
			this.engine = engine;
			return this;
		}

		public ExecutionBuilder screen(Screen screen) {
			this.screen = screen;
			return this;
		}

		public ExecutionBuilder target(Target target) {
			this.target = target;
			return this;
		}

		public ExecutionBuilder manager(Manager manager) {
			this.manager = manager;
			return this;
		}

		public ExecutionBuilder delay(Delay delay) {
			this.delay = delay;
			return this;
		}

		public ExecutionBuilder video(Video video) {
			this.video = video;
			return this;
		}

		public Execution build() {
			return new Execution(this);
		}
	}

}