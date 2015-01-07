package com.github.kmbulebu.nicknack.core.actions;

import java.util.List;
import java.util.UUID;

import com.github.kmbulebu.nicknack.core.attributes.AttributeDefinition;

public interface ActionDefinition {
	
	public UUID getUUID();
	
	public UUID getProviderUUID();
	
	public String getName();
	
	public List<AttributeDefinition> getAttributeDefinitions();

}