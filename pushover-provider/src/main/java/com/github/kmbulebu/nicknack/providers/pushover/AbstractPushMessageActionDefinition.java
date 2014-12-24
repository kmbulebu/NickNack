package com.github.kmbulebu.nicknack.providers.pushover;

import java.util.Arrays;
import java.util.UUID;

import com.github.kmbulebu.nicknack.core.actions.Action;
import com.github.kmbulebu.nicknack.core.actions.ActionFailureException;
import com.github.kmbulebu.nicknack.core.actions.ActionParameterException;
import com.github.kmbulebu.nicknack.core.actions.BasicActionDefinition;
import com.github.kmbulebu.nicknack.core.actions.ParameterDefinition;

public abstract class AbstractPushMessageActionDefinition extends BasicActionDefinition {
	
	public AbstractPushMessageActionDefinition(UUID uuid, String name, ParameterDefinition... parameterDefinitions) {
		super(uuid, PushOverProvider.PROVIDER_UUID, name, appendCommonParameterDefinition(parameterDefinitions));
	}
	
	private static ParameterDefinition[] appendCommonParameterDefinition(ParameterDefinition... parameterDefinitions) {
		final ParameterDefinition[] definitions = Arrays.copyOf(parameterDefinitions, parameterDefinitions.length + 3);
		definitions[definitions.length - 3] = MessageParameterDefinition.INSTANCE;
		definitions[definitions.length - 2] = UserParameterDefinition.INSTANCE;
		definitions[definitions.length - 1] = TokenParameterDefinition.INSTANCE;
		return definitions;
	}
	
	public abstract void run(Action action) throws ActionFailureException, ActionParameterException;

}
