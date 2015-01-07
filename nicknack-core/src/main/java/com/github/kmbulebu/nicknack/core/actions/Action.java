package com.github.kmbulebu.nicknack.core.actions;

import java.util.UUID;

import com.github.kmbulebu.nicknack.core.attributes.AttributeCollection;


public interface Action extends AttributeCollection {
	
	public UUID getAppliesToActionDefinition();

}
