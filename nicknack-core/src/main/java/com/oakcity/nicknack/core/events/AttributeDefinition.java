package com.oakcity.nicknack.core.events;

import java.util.UUID;

import com.oakcity.nicknack.core.units.Unit;

public interface AttributeDefinition {
	
	public UUID getUUID();
	
	public String getName();
	
	public Unit getUnits();
	
	public boolean isOptional();
}