package com.github.kmbulebu.nicknack.providers.pushover.actions;

import java.util.Arrays;
import java.util.UUID;

import com.github.kmbulebu.nicknack.core.actions.Action;
import com.github.kmbulebu.nicknack.core.actions.ActionFailureException;
import com.github.kmbulebu.nicknack.core.actions.ActionParameterException;
import com.github.kmbulebu.nicknack.core.actions.BasicActionDefinition;
import com.github.kmbulebu.nicknack.core.attributes.AttributeDefinition;
import com.github.kmbulebu.nicknack.providers.pushover.attributes.MessageAttributeDefinition;
import com.github.kmbulebu.nicknack.providers.pushover.attributes.TokenAttributeDefinition;
import com.github.kmbulebu.nicknack.providers.pushover.attributes.UserAttributeDefinition;

public abstract class AbstractPushMessageActionDefinition extends BasicActionDefinition {
	
	public AbstractPushMessageActionDefinition(UUID uuid, String name, String description, AttributeDefinition... attributeDefinitions) {
		super(uuid, name, description, appendCommonAttributeDefinitions(attributeDefinitions));
	}
	
	private static AttributeDefinition[] appendCommonAttributeDefinitions(AttributeDefinition... attributeDefinitions) {
		final AttributeDefinition[] definitions = Arrays.copyOf(attributeDefinitions, attributeDefinitions.length + 3);
		definitions[definitions.length - 3] = MessageAttributeDefinition.INSTANCE;
		definitions[definitions.length - 2] = UserAttributeDefinition.INSTANCE;
		definitions[definitions.length - 1] = TokenAttributeDefinition.INSTANCE;
		return definitions;
	}
	
	public abstract void run(Action action) throws ActionFailureException, ActionParameterException;

}
