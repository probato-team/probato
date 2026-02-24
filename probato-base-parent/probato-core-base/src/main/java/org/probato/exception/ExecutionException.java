package org.probato.exception;

import java.text.MessageFormat;

public class ExecutionException extends RuntimeException {

	private static final long serialVersionUID = 1202146471210629481L;

	public ExecutionException(String message, Object... params) {
		super(MessageFormat.format(message, params));
	}

}