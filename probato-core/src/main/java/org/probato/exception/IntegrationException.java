package org.probato.exception;

public class IntegrationException extends ProbatoException {

	private static final long serialVersionUID = -2666298530730967591L;

	public IntegrationException(String message, Object... params) {
		super(message, params);
	}

}