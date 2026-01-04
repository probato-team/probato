package org.probato.exception;

import java.text.MessageFormat;

public class IntegrityException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = 5262253895642408562L;

	public IntegrityException(String message, Object... params) {
		super(MessageFormat.format(message, params));
	}

}