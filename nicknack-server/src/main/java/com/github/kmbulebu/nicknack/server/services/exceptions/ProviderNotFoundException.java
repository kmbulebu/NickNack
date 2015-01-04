package com.github.kmbulebu.nicknack.server.services.exceptions;

import java.util.UUID;

import com.github.kmbulebu.nicknack.core.providers.Provider;

public class ProviderNotFoundException extends ResourceNotFoundException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5377153584663402285L;

	public ProviderNotFoundException(String uuid) {
		super(Provider.class.getSimpleName(), uuid);
	}

	public ProviderNotFoundException(UUID uuid) {
		this(uuid.toString());
	}
	
	public ProviderNotFoundException(String lookupType, String uuid) {
		this(lookupType + ": " + uuid);
	}
	
	public ProviderNotFoundException(String lookupType, UUID uuid) {
		this(lookupType, uuid.toString());
	}
}
