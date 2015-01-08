package com.github.kmbulebu.nicknack.providers.wemo.actions;

import java.util.Arrays;
import java.util.UUID;

import com.github.kmbulebu.nicknack.core.actions.Action;
import com.github.kmbulebu.nicknack.core.actions.ActionFailureException;
import com.github.kmbulebu.nicknack.core.actions.ActionParameterException;
import com.github.kmbulebu.nicknack.core.actions.BasicActionDefinition;
import com.github.kmbulebu.nicknack.core.attributes.AttributeDefinition;
import com.github.kmbulebu.nicknack.providers.wemo.attributes.FriendlyNameAttributeDefinition;

public abstract class AbstractWemoActionDefinition extends BasicActionDefinition {
	
	public AbstractWemoActionDefinition(UUID uuid, String name, String description, AttributeDefinition... attributeDefinitions) {
		super(uuid, name, description, appendCommonAttributeDefinition(attributeDefinitions));
	}
	
	private static AttributeDefinition[] appendCommonAttributeDefinition(AttributeDefinition... attributeDefinitions) {
		final AttributeDefinition[] definitions = Arrays.copyOf(attributeDefinitions, attributeDefinitions.length + 1);
		definitions[definitions.length - 1] = FriendlyNameAttributeDefinition.INSTANCE;
		return definitions;
	}
	
	public abstract void run(Action action) throws ActionFailureException, ActionParameterException;

}
