package org.probato.exception;

public class IntegrityException extends ProbatoException {

	private static final long serialVersionUID = -2666298530730967591L;

	public IntegrityException(String message, Object... params) {
		super(message, params);
	}

}