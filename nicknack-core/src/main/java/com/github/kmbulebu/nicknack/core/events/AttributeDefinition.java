package com.github.kmbulebu.nicknack.core.events;

import java.util.UUID;

import com.github.kmbulebu.nicknack.core.units.Unit;

public interface AttributeDefinition {
	
	public UUID getUUID();
	
	public String getName();
	
	public Unit getUnits();
	
	public boolean isOptional();
}