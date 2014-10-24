package com.github.kmbulebu.nicknack.providers.ssh;

import java.util.Arrays;
import java.util.UUID;

import com.github.kmbulebu.nicknack.core.actions.Action;
import com.github.kmbulebu.nicknack.core.actions.ActionFailureException;
import com.github.kmbulebu.nicknack.core.actions.ActionParameterException;
import com.github.kmbulebu.nicknack.core.actions.BasicActionDefinition;
import com.github.kmbulebu.nicknack.core.actions.ParameterDefinition;

public abstract class SshActionDefinition extends BasicActionDefinition {
	
	public SshActionDefinition(UUID uuid, String name, ParameterDefinition... parameterDefinitions) {
		super(uuid, SshProvider.PROVIDER_UUID, name, appendCommonParameterDefinition(parameterDefinitions));
	}
	
	private static ParameterDefinition[] appendCommonParameterDefinition(ParameterDefinition... parameterDefinitions) {
		final ParameterDefinition[] definitions = Arrays.copyOf(parameterDefinitions, parameterDefinitions.length + 4);
		definitions[definitions.length - 4] = HostParameterDefinition.INSTANCE;
		definitions[definitions.length - 3] = PortParameterDefinition.INSTANCE;
		definitions[definitions.length - 2] = UserNameParameterDefinition.INSTANCE;
		definitions[definitions.length - 1] = PasswordParameterDefinition.INSTANCE;
		return definitions;
	}
	
	public abstract void run(Action action) throws ActionFailureException, ActionParameterException;

}
