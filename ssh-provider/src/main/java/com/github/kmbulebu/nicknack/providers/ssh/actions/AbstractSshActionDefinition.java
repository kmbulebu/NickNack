package com.github.kmbulebu.nicknack.providers.ssh.actions;

import java.util.Arrays;
import java.util.UUID;

import com.github.kmbulebu.nicknack.core.actions.Action;
import com.github.kmbulebu.nicknack.core.actions.ActionFailureException;
import com.github.kmbulebu.nicknack.core.actions.ActionParameterException;
import com.github.kmbulebu.nicknack.core.actions.BasicActionDefinition;
import com.github.kmbulebu.nicknack.core.attributes.AttributeDefinition;
import com.github.kmbulebu.nicknack.providers.ssh.attributes.HostAttributeDefinition;
import com.github.kmbulebu.nicknack.providers.ssh.attributes.PasswordAttributeDefinition;
import com.github.kmbulebu.nicknack.providers.ssh.attributes.PortAttributeDefinition;
import com.github.kmbulebu.nicknack.providers.ssh.attributes.UserNameAttributeDefinition;

public abstract class AbstractSshActionDefinition extends BasicActionDefinition {
	
	public AbstractSshActionDefinition(UUID uuid, String name, String description, AttributeDefinition... attributeDefinitions) {
		super(uuid,name, description, appendCommonAttributeDefinition(attributeDefinitions));
	}
	
	private static AttributeDefinition[] appendCommonAttributeDefinition(AttributeDefinition... attributeDefinitions) {
		final AttributeDefinition[] definitions = Arrays.copyOf(attributeDefinitions, attributeDefinitions.length + 4);
		definitions[definitions.length - 4] = HostAttributeDefinition.INSTANCE;
		definitions[definitions.length - 3] = PortAttributeDefinition.INSTANCE;
		definitions[definitions.length - 2] = UserNameAttributeDefinition.INSTANCE;
		definitions[definitions.length - 1] = PasswordAttributeDefinition.INSTANCE;
		return definitions;
	}
	
	public abstract void run(Action action) throws ActionFailureException, ActionParameterException;

}
