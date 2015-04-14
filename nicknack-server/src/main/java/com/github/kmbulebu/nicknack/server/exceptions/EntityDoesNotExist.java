package com.github.kmbulebu.nicknack.server.exceptions;


public class EntityDoesNotExist extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public EntityDoesNotExist(String entityType) {
		super("Could not find " + entityType);
	}
	

}
