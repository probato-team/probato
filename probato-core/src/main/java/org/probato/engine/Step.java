package org.probato.engine;

import java.io.Serializable;

public class Step implements Serializable {

	private static final long serialVersionUID = 4696254860832551855L;

	private Integer sequence;
	private String text;

	Step(StepBuilder builder) {
		this.sequence = builder.sequence;
		this.text = builder.text;
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

	static class StepBuilder {

		private Integer sequence;
		private String text;

		private StepBuilder() {
		}

		public StepBuilder sequence(Integer sequence) {
			this.sequence = sequence;
			return this;
		}

		public StepBuilder text(String text) {
			this.text = text;
			return this;
		}

		public Step build() {
			return new Step(this);
		}
	}

}