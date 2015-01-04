package com.github.kmbulebu.nicknack.server.services.exceptions;

import java.util.UUID;

import com.github.kmbulebu.nicknack.core.states.StateDefinition;

public class StateDefinitionNotFoundException extends ResourceNotFoundException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5377153584663402285L;

	public StateDefinitionNotFoundException(String uuid) {
		super(StateDefinition.class.getSimpleName(), uuid);
	}

	public StateDefinitionNotFoundException(UUID uuid) {
		this(uuid.toString());
	}
}
