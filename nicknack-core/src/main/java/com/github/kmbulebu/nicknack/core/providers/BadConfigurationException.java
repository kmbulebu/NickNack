package com.github.kmbulebu.nicknack.core.providers;

public class BadConfigurationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BadConfigurationException(String message, Throwable cause) {
		super(message, cause);
	}

	public BadConfigurationException(String message) {
		super(message);
	}

	public BadConfigurationException(Throwable cause) {
		super(cause);
	}

	
}
