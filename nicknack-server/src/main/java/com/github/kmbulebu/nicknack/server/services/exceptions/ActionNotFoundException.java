package com.github.kmbulebu.nicknack.server.services.exceptions;

import java.util.UUID;

import com.github.kmbulebu.nicknack.core.actions.Action;

public class ActionNotFoundException extends ResourceNotFoundException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5377153584663402285L;

	public ActionNotFoundException(String uuid) {
		super(Action.class.getSimpleName(), uuid);
	}

	public ActionNotFoundException(UUID uuid) {
		this(uuid.toString());
	}
}
