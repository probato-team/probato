package org.probato.entity.model;

public class Delay {

	private Integer waitingTimeout = 10_000;
	private Integer actionInterval = 100;
	
	public Delay() {}

	public Delay(DelayBuilder builder) {
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