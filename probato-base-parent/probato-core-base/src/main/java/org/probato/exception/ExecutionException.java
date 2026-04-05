package org.probato.exception;

import java.text.MessageFormat;

public class ExecutionException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = 2711208450688335345L;

	public ExecutionException(String message, Object... params) {
		super(MessageFormat.format(message, params));
	}

}