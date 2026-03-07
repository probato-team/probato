package org.probato.browser;

/**
 * Represents an active browser session managed by the Probato framework.
 *
 * <p>
 * A {@code BrowserSession} controls the lifecycle of a browser instance, from
 * initialization to destruction.
 * </p>
 *
 * <p>
 * This interface intentionally hides the underlying automation technology.
 * </p>
 */
public interface BrowserSession {

	/**
	 * Starts or executes the browser session.
	 *
	 * <p>
	 * Implementations may initialize drivers, contexts or pages during this phase.
	 * </p>
	 */
	void run();

	/**
	 * Terminates the browser session and releases all allocated resources.
	 *
	 * <p>
	 * This method must be idempotent.
	 * </p>
	 */
	void destroy();

	/**
	 * Returns a human-readable description of the session implementation.
	 *
	 * @return session description
	 */
	String description();

	/**
	 * Returns the version of the underlying automation engine.
	 *
	 * @return engine version
	 */
	String version();

}