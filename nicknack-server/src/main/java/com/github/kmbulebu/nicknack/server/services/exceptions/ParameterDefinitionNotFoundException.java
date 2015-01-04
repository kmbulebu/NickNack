package com.github.kmbulebu.nicknack.server.services.exceptions;

import java.util.UUID;

import com.github.kmbulebu.nicknack.core.actions.ParameterDefinition;

public class ParameterDefinitionNotFoundException extends ResourceNotFoundException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5377153584663402285L;

	public ParameterDefinitionNotFoundException(String uuid) {
		super(ParameterDefinition.class.getSimpleName(), uuid);
	}

	public ParameterDefinitionNotFoundException(UUID uuid) {
		this(uuid.toString());
	}
}
