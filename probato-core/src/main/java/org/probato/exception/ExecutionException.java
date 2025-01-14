package org.probato.exception;

public class ExecutionException extends PrrobatoException {

	private static final long serialVersionUID = 1202146471210629481L;

	public ExecutionException(String message, Object... params) {
		super(message, params);
	}

}