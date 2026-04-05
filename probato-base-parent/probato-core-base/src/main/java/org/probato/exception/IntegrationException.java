package org.probato.exception;

import java.text.MessageFormat;

public class IntegrationException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = 4242568775262163502L;

	public IntegrationException(String message, Object... params) {
		super(MessageFormat.format(message, params));
	}

}