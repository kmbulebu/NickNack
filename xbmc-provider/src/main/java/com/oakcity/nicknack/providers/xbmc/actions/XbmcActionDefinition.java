package com.oakcity.nicknack.providers.xbmc.actions;

import java.util.Arrays;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.oakcity.nicknack.core.actions.Action;
import com.oakcity.nicknack.core.actions.ActionFailureException;
import com.oakcity.nicknack.core.actions.ActionParameterException;
import com.oakcity.nicknack.core.actions.BasicActionDefinition;
import com.oakcity.nicknack.core.actions.ParameterDefinition;
import com.oakcity.nicknack.providers.xbmc.XbmcClient;
import com.oakcity.nicknack.providers.xbmc.XbmcProvider;
import com.oakcity.nicknack.providers.xbmc.actions.parameters.HostParameterDefinition;

public abstract class XbmcActionDefinition extends BasicActionDefinition {
	
	private static final Logger logger = LogManager.getLogger(XbmcProvider.LOGGER_NAME);
	
	public XbmcActionDefinition(UUID uuid, String name, ParameterDefinition... parameterDefinitions) {
		super(uuid, XbmcProvider.PROVIDER_UUID, name, appendHostParameterDefinition(parameterDefinitions));
	}
	
	private static ParameterDefinition[] appendHostParameterDefinition(ParameterDefinition... parameterDefinitions) {
		if (logger.isTraceEnabled()) {
			logger.entry((Object[]) parameterDefinitions);
		}
		final ParameterDefinition[] definitions = Arrays.copyOf(parameterDefinitions, parameterDefinitions.length + 1);
		definitions[definitions.length - 1] = HostParameterDefinition.INSTANCE;
		if (logger.isTraceEnabled()) {
			logger.exit(definitions);
		}
		return definitions;
	}
	
	public abstract void run(Action action, XbmcClient client) throws ActionFailureException, ActionParameterException;

}
