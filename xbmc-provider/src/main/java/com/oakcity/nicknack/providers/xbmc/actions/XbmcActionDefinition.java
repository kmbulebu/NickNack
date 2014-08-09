package com.oakcity.nicknack.providers.xbmc.actions;

import java.util.Arrays;
import java.util.UUID;

import com.oakcity.nicknack.core.actions.Action;
import com.oakcity.nicknack.core.actions.ActionFailureException;
import com.oakcity.nicknack.core.actions.ActionParameterException;
import com.oakcity.nicknack.core.actions.BasicActionDefinition;
import com.oakcity.nicknack.core.actions.ParameterDefinition;
import com.oakcity.nicknack.providers.xbmc.XbmcClient;
import com.oakcity.nicknack.providers.xbmc.XbmcProvider;
import com.oakcity.nicknack.providers.xbmc.actions.parameters.HostParameterDefinition;

public abstract class XbmcActionDefinition extends BasicActionDefinition {
	
	public XbmcActionDefinition(UUID uuid, String name, ParameterDefinition... parameterDefinitions) {
		super(uuid, XbmcProvider.PROVIDER_UUID, name, appendHostParameterDefinition(parameterDefinitions));
	}
	
	private static ParameterDefinition[] appendHostParameterDefinition(ParameterDefinition... parameterDefinitions) {
		final ParameterDefinition[] definitions = Arrays.copyOf(parameterDefinitions, parameterDefinitions.length + 1);
		definitions[definitions.length - 1] = HostParameterDefinition.INSTANCE;
		return definitions;
	}
	
	public abstract void run(Action action, XbmcClient client) throws ActionFailureException, ActionParameterException;

}
