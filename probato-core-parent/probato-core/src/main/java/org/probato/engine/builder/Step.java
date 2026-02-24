package org.probato.engine.builder;

import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;
import java.util.Objects;

public class Step implements Serializable {

	private static final long serialVersionUID = 4696254860832551855L;

	private Integer sequence;
	private String text;
	private Long duration;
	private String clazz;
	private String method;
	private String error;

	Step(StepBuilder builder) {
		this.sequence = builder.sequence;
		this.text = builder.text;
		this.clazz = builder.clazz;
		this.method = builder.method;
		this.error = builder.error;
		this.duration = Objects.nonNull(builder.start) && Objects.nonNull(builder.end)
				? Duration.between(builder.start, builder.end).toMillis()
				: null;
	}

	public static StepBuilder builder() {
		return new StepBuilder();
	}

	public Integer getSequence() {
		return sequence;
	}

	public String getText() {
		return text;
	}

	public String getClazz() {
		return clazz;
	}

	public String getMethod() {
		return method;
	}

	public String getError() {
		return error;
	}

	public Long getRuntime() {
		return duration;
	}

	public static class StepBuilder {

		private Integer sequence;
		private String text;
		private Instant start;
		private Instant end;
		private String clazz;
		private String method;
		private String error;

		private StepBuilder() {}

		public StepBuilder sequence(Integer sequence) {
			this.sequence = sequence;
			return this;
		}

		public StepBuilder text(String text) {
			this.text = text;
			return this;
		}

		public StepBuilder start(Instant start) {
			this.start = start;
			return this;
		}
		public StepBuilder end(Instant end) {
			this.end = end;
			return this;
		}
		public StepBuilder clazz(String clazz) {
			this.clazz = clazz;
			return this;
		}
		public StepBuilder method(String method) {
			this.method = method;
			return this;
		}
		public StepBuilder error(String error) {
			this.error = error;
			return this;
		}

		public Step build() {
			return new Step(this);
		}
	}

}