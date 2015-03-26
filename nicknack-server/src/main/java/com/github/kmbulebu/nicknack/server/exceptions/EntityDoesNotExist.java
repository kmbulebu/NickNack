package com.github.kmbulebu.nicknack.server.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class EntityDoesNotExist extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public EntityDoesNotExist(String entityType) {
		super("Could not find " + entityType);
	}
	

}
