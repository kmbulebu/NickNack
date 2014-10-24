package com.github.kmbulebu.nicknack.core.actions;

import java.text.ParseException;
import java.util.Collection;
import java.util.UUID;

import com.github.kmbulebu.nicknack.core.units.Unit;

public interface ParameterDefinition {
	
	public UUID getUUID();
	
	public String getName();
	
	public Unit getUnits();
	
	public boolean isRequired();
	
	// Cleans up a user enter value. 
	public String format(String rawValue) throws ParseException;
	
	// Returns an empty collection for a valid value, or a collection of error messages.
	public Collection<String> validate(String value);
	
}