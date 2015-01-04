package com.github.kmbulebu.nicknack.server.services.exceptions;

import java.util.UUID;

import com.github.kmbulebu.nicknack.core.states.filters.StateFilter;

public class StateFilterNotFoundException extends ResourceNotFoundException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5377153584663402285L;

	public StateFilterNotFoundException(String uuid) {
		super(StateFilter.class.getSimpleName(), uuid);
	}

	public StateFilterNotFoundException(UUID uuid) {
		this(uuid.toString());
	}
}
