package com.github.kmbulebu.nicknack.core.providers;

public class ProviderFailureException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ProviderFailureException(String message, Throwable cause) {
		super(message, cause);
	}

	public ProviderFailureException(String message) {
		super(message);
	}

	public ProviderFailureException(Throwable cause) {
		super(cause);
	}
	
	

}
