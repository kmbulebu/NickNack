package com.github.kmbulebu.nicknack.providers.xbmc.actions;

import java.util.Arrays;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.kmbulebu.nicknack.core.actions.Action;
import com.github.kmbulebu.nicknack.core.actions.ActionFailureException;
import com.github.kmbulebu.nicknack.core.actions.ActionParameterException;
import com.github.kmbulebu.nicknack.core.actions.BasicActionDefinition;
import com.github.kmbulebu.nicknack.core.attributes.AttributeDefinition;
import com.github.kmbulebu.nicknack.providers.xbmc.XbmcProvider;
import com.github.kmbulebu.nicknack.providers.xbmc.attributes.HostAttributeDefinition;
import com.github.kmbulebu.nicknack.providers.xbmc.internal.XbmcClient;

public abstract class AbstractXbmcActionDefinition extends BasicActionDefinition {
	
	private static final Logger logger = LogManager.getLogger(XbmcProvider.LOGGER_NAME);
	
	public AbstractXbmcActionDefinition(UUID uuid, String name, String description, AttributeDefinition... attributeDefinitions) {
		super(uuid, name, description, appendHostAttributeDefinition(attributeDefinitions));
	}
	
	private static AttributeDefinition[] appendHostAttributeDefinition(AttributeDefinition... attributeDefinitions) {
		if (logger.isTraceEnabled()) {
			logger.entry((Object[]) attributeDefinitions);
		}
		final AttributeDefinition[] definitions = Arrays.copyOf(attributeDefinitions, attributeDefinitions.length + 1);
		definitions[definitions.length - 1] = HostAttributeDefinition.INSTANCE;
		if (logger.isTraceEnabled()) {
			logger.exit(definitions);
		}
		return definitions;
	}
	
	public abstract void run(Action action, XbmcClient client) throws ActionFailureException, ActionParameterException;

}
