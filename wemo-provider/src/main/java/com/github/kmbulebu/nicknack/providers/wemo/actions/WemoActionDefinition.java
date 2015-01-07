package com.github.kmbulebu.nicknack.providers.wemo.actions;

import java.util.Arrays;
import java.util.UUID;

import com.github.kmbulebu.nicknack.core.actions.Action;
import com.github.kmbulebu.nicknack.core.actions.ActionFailureException;
import com.github.kmbulebu.nicknack.core.actions.ActionParameterException;
import com.github.kmbulebu.nicknack.core.actions.BasicActionDefinition;
import com.github.kmbulebu.nicknack.core.attributes.AttributeDefinition;
import com.github.kmbulebu.nicknack.providers.wemo.WemoProvider;
import com.github.kmbulebu.nicknack.providers.wemo.attributes.FriendlyNameAttributeDefinition;

public abstract class WemoActionDefinition extends BasicActionDefinition {
	
	public WemoActionDefinition(UUID uuid, String name, AttributeDefinition... parameterDefinitions) {
		super(uuid, WemoProvider.PROVIDER_UUID, name, appendCommonAttributeDefinition(parameterDefinitions));
	}
	
	private static AttributeDefinition[] appendCommonAttributeDefinition(AttributeDefinition... parameterDefinitions) {
		final AttributeDefinition[] definitions = Arrays.copyOf(parameterDefinitions, parameterDefinitions.length + 1);
		definitions[definitions.length - 1] = FriendlyNameAttributeDefinition.INSTANCE;
		return definitions;
	}
	
	public abstract void run(Action action) throws ActionFailureException, ActionParameterException;

}
