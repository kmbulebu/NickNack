package com.oakcity.nicknack.providers.xbmc.actions.parameters;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.oakcity.nicknack.core.actions.parameters.BasicParameterDefinition;
import com.oakcity.nicknack.core.units.StringUnit;
import com.oakcity.nicknack.providers.xbmc.XbmcProvider;

public class HostParameterDefinition extends BasicParameterDefinition<StringUnit> {

	private static final Logger logger = LogManager.getLogger(XbmcProvider.LOGGER_NAME);
	
	public static final UUID DEF_UUID = UUID.fromString("4e1bd491-fd3d-47bf-9910-8cf79b139b1d");
	
	public static final HostParameterDefinition INSTANCE = new HostParameterDefinition();

	public HostParameterDefinition() {
		super(DEF_UUID, "Host", StringUnit.INSTANCE, true);
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
		// Dumb validation for now. Will get better when we implement a value chooser.
		if (value == null || value.isEmpty()) {
			return Collections.singleton("A host value must be provided.");
		}
		return Collections.emptyList();
	}

}
