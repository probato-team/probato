package org.probato.model;

import org.probato.configuration.ConfigurationResolver;

/**
 * Defines timeout and delay settings used during browser execution.
 *
 * <p>Default values are applied when no system properties are provided.</p>
 */
public class Delay {

	private Integer waiting;
	private Integer actionInterval;

	public Delay() {

		this.waiting =  ConfigurationResolver
				.executionProperty("execution.timeout.waiting")
				.map(Integer::valueOf)
				.orElse(10_000);

		this.actionInterval =  ConfigurationResolver
				.executionProperty("execution.timeout.actionInterval")
				.map(Integer::valueOf)
				.orElse(100);
	}

	public Integer getWaiting() {
		return waiting;
	}

	public Integer getActionInterval() {
		return actionInterval;
	}

}