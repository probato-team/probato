package org.probato.model;

/**
 * Defines timeout and delay settings used during browser execution.
 *
 * <p>Default values are applied when no system properties are provided.</p>
 */
public class Delay {

	private Integer waiting;
	private Integer actionInterval;

	public Delay() {}

	public Delay(Integer waiting, Integer actionInterval) {
		this();
		this.waiting = waiting;
		this.actionInterval = actionInterval;
	}

	public Integer getWaiting() {
		return waiting;
	}

	public void setWaiting(Integer waiting) {
		this.waiting = waiting;
	}

	public Integer getActionInterval() {
		return actionInterval;
	}

	public void setActionInterval(Integer actionInterval) {
		this.actionInterval = actionInterval;
	}

}