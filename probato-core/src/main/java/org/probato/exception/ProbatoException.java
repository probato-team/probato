package org.probato.exception;

import java.text.MessageFormat;

public class ProbatoException extends RuntimeException {

	private static final long serialVersionUID = -7863419021603385683L;

	public ProbatoException(String message, Object... params) {
		super(MessageFormat.format(message, params));
	}

}