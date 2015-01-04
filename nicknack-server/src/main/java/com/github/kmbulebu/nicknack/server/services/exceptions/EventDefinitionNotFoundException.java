package com.github.kmbulebu.nicknack.server.services.exceptions;

import java.util.UUID;

import com.github.kmbulebu.nicknack.core.events.EventDefinition;

public class EventDefinitionNotFoundException extends ResourceNotFoundException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5377153584663402285L;

	public EventDefinitionNotFoundException(String uuid) {
		super(EventDefinition.class.getSimpleName(), uuid);
	}

	public EventDefinitionNotFoundException(UUID uuid) {
		this(uuid.toString());
	}
}
