package org.probato.entity.model;

import org.probato.entity.type.Screen;

public class Execution {

	private Long increment;
	private Screen screen = Screen.PRINCIPAL;
	private Target target;
	private Manager manager;
	private Delay delay;
	private Video video;
	private Directory directory;

	public Execution() {}

	public Execution(ExecutionBuilder builder) {
		this.increment = builder.increment;
		this.screen = builder.screen;
		this.target = builder.target;
		this.manager = builder.manager;
		this.delay = builder.delay;
		this.video = builder.video;
		this.directory = builder.directory;
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

		private Long increment;
		private Screen screen = Screen.PRINCIPAL;
		private Target target;
		private Manager manager;
		private Delay delay;
		private Video video;
		private Directory directory;

		private ExecutionBuilder() {
		}

		public ExecutionBuilder increment(Long increment) {
			this.increment = increment;
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

		public ExecutionBuilder directory(Directory directory) {
			this.directory = directory;
			return this;
		}

		public Execution build() {
			return new Execution(this);
		}
	}

}