package com.github.kmbulebu.nicknack.server.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public abstract class ResourceNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6298389215380422039L;
	
	protected ResourceNotFoundException(String resourceType, String identifier, Throwable t) {
		super("Could not find " + resourceType + " with identifier=" + identifier, t);
	}
	
	protected ResourceNotFoundException(String resourceType, String identifier) {
		this(resourceType, identifier, null);
	}

}
