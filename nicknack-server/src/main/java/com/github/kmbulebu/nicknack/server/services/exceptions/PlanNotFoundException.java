package com.github.kmbulebu.nicknack.server.services.exceptions;

import java.util.UUID;

import com.github.kmbulebu.nicknack.core.plans.Plan;

public class PlanNotFoundException extends ResourceNotFoundException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5377153584663402285L;

	public PlanNotFoundException(String uuid) {
		super(Plan.class.getSimpleName(), uuid);
	}

	public PlanNotFoundException(UUID uuid) {
		this(uuid.toString());
	}
}
