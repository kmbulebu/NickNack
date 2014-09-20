package com.oakcity.nicknack.providers.xbmc.actions.parameters;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.oakcity.nicknack.core.actions.parameters.BasicParameterDefinition;
import com.oakcity.nicknack.core.units.StringUnit;
import com.oakcity.nicknack.providers.xbmc.XbmcProvider;

public class MessageParameterDefinition extends BasicParameterDefinition<StringUnit> {
	
	private static final Logger logger = LogManager.getLogger(XbmcProvider.LOGGER_NAME);
	
	public static final UUID DEF_UUID = UUID.fromString("b505ad85-1261-4d29-999b-4120dfb451ca");
	
	public static final MessageParameterDefinition INSTANCE = new MessageParameterDefinition();

	public MessageParameterDefinition() {
		super(DEF_UUID, "Message", StringUnit.INSTANCE, true);
	}

	@Override
	public String format(String rawValue) {
		return rawValue;
	}

	@Override
	public Collection<String> validate(String value) {
		if (logger.isTraceEnabled()) {
			logger.entry(value);
		}
		return Collections.emptyList();
	}

}
