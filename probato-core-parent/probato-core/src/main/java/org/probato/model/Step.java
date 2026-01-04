package org.probato.model;

/**
 * Represents a single executable step.
 */
public interface Step {

	/**
	 * @return step code
	 */
	String code();

	/**
	 * @return human-readable action description
	 */
	String description();

}