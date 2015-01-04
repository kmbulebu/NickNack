package com.github.kmbulebu.nicknack.server.services.exceptions;

import java.util.UUID;

import com.github.kmbulebu.nicknack.core.attributes.AttributeDefinition;

public class AttributeDefinitionNotFoundException extends ResourceNotFoundException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5377153584663402285L;

	public AttributeDefinitionNotFoundException(String uuid) {
		super(AttributeDefinition.class.getSimpleName(), uuid);
	}

	public AttributeDefinitionNotFoundException(UUID uuid) {
		this(uuid.toString());
	}
}
