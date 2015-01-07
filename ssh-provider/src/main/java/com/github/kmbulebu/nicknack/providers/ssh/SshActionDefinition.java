package com.github.kmbulebu.nicknack.providers.ssh;

import java.util.Arrays;
import java.util.UUID;

import com.github.kmbulebu.nicknack.core.actions.Action;
import com.github.kmbulebu.nicknack.core.actions.ActionFailureException;
import com.github.kmbulebu.nicknack.core.actions.ActionParameterException;
import com.github.kmbulebu.nicknack.core.actions.BasicActionDefinition;
import com.github.kmbulebu.nicknack.core.attributes.AttributeDefinition;

public abstract class SshActionDefinition extends BasicActionDefinition {
	
	public SshActionDefinition(UUID uuid, String name, AttributeDefinition... parameterDefinitions) {
		super(uuid, SshProvider.PROVIDER_UUID, name, appendCommonAttributeDefinition(parameterDefinitions));
	}
	
	private static AttributeDefinition[] appendCommonAttributeDefinition(AttributeDefinition... parameterDefinitions) {
		final AttributeDefinition[] definitions = Arrays.copyOf(parameterDefinitions, parameterDefinitions.length + 4);
		definitions[definitions.length - 4] = HostAttributeDefinition.INSTANCE;
		definitions[definitions.length - 3] = PortAttributeDefinition.INSTANCE;
		definitions[definitions.length - 2] = UserNameAttributeDefinition.INSTANCE;
		definitions[definitions.length - 1] = PasswordAttributeDefinition.INSTANCE;
		return definitions;
	}
	
	public abstract void run(Action action) throws ActionFailureException, ActionParameterException;

}
