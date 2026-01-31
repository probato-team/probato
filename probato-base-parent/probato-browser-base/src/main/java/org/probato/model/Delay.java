package org.probato.model;

/**
 * Defines timeout and delay settings used during browser execution.
 *
 * <p>Default values are applied when no system properties are provided.</p>
 */
public class Delay {

	private Integer waitingTimeout;
	private Integer actionInterval;

	public Delay() {}

	public Delay(Integer waitingTimeout, Integer actionInterval) {
		this();
		this.waitingTimeout = waitingTimeout;
		this.actionInterval = actionInterval;
	}

	public Delay(DelayBuilder builder) {
		this();
		this.waitingTimeout = builder.waitingTimeout;
		this.actionInterval = builder.actionInterval;
	}

	public Integer getWaitingTimeout() {
		return waitingTimeout;
	}

	public void setWaitingTimeout(Integer waitingTimeout) {
		this.waitingTimeout = waitingTimeout;
	}

	public Integer getActionInterval() {
		return actionInterval;
	}

	public void setActionInterval(Integer actionInterval) {
		this.actionInterval = actionInterval;
	}

	public static DelayBuilder builder() {
		return new DelayBuilder();
	}

	public static class DelayBuilder {

		private Integer waitingTimeout;
		private Integer actionInterval;

		private DelayBuilder() {}

		public DelayBuilder waitingTimeout(Integer waitingTimeout) {
			this.waitingTimeout = waitingTimeout;
			return this;
		}

		public DelayBuilder actionInterval(Integer actionInterval) {
			this.actionInterval = actionInterval;
			return this;
		}

		public Delay build() {
			return new Delay(this);
		}
	}

}