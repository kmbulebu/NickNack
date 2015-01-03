package com.github.kmbulebu.nicknack.providers.wemo.actions;

import java.util.Arrays;
import java.util.UUID;

import com.github.kmbulebu.nicknack.core.actions.Action;
import com.github.kmbulebu.nicknack.core.actions.ActionFailureException;
import com.github.kmbulebu.nicknack.core.actions.ActionParameterException;
import com.github.kmbulebu.nicknack.core.actions.BasicActionDefinition;
import com.github.kmbulebu.nicknack.core.actions.ParameterDefinition;
import com.github.kmbulebu.nicknack.providers.wemo.WemoProvider;

public abstract class WemoActionDefinition extends BasicActionDefinition {
	
	public WemoActionDefinition(UUID uuid, String name, ParameterDefinition... parameterDefinitions) {
		super(uuid, WemoProvider.PROVIDER_UUID, name, appendCommonParameterDefinition(parameterDefinitions));
	}
	
	private static ParameterDefinition[] appendCommonParameterDefinition(ParameterDefinition... parameterDefinitions) {
		final ParameterDefinition[] definitions = Arrays.copyOf(parameterDefinitions, parameterDefinitions.length + 1);
		definitions[definitions.length - 1] = FriendlyNameParameterDefinition.INSTANCE;
		return definitions;
	}
	
	public abstract void run(Action action) throws ActionFailureException, ActionParameterException;

}
