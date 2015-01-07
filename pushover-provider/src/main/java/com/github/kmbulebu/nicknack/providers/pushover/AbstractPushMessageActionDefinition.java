package com.github.kmbulebu.nicknack.providers.pushover;

import java.util.Arrays;
import java.util.UUID;

import com.github.kmbulebu.nicknack.core.actions.Action;
import com.github.kmbulebu.nicknack.core.actions.ActionFailureException;
import com.github.kmbulebu.nicknack.core.actions.ActionParameterException;
import com.github.kmbulebu.nicknack.core.actions.BasicActionDefinition;
import com.github.kmbulebu.nicknack.core.attributes.AttributeDefinition;

public abstract class AbstractPushMessageActionDefinition extends BasicActionDefinition {
	
	public AbstractPushMessageActionDefinition(UUID uuid, String name, AttributeDefinition... parameterDefinitions) {
		super(uuid, PushOverProvider.PROVIDER_UUID, name, appendCommonAttributeDefinitions(parameterDefinitions));
	}
	
	private static AttributeDefinition[] appendCommonAttributeDefinitions(AttributeDefinition... parameterDefinitions) {
		final AttributeDefinition[] definitions = Arrays.copyOf(parameterDefinitions, parameterDefinitions.length + 3);
		definitions[definitions.length - 3] = MessageAttributeDefinition.INSTANCE;
		definitions[definitions.length - 2] = UserAttributeDefinition.INSTANCE;
		definitions[definitions.length - 1] = TokenAttributeDefinition.INSTANCE;
		return definitions;
	}
	
	public abstract void run(Action action) throws ActionFailureException, ActionParameterException;

}
