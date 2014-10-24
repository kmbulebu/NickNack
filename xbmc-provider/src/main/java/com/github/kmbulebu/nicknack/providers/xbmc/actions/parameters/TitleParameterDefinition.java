package com.github.kmbulebu.nicknack.providers.xbmc.actions.parameters;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.kmbulebu.nicknack.core.actions.parameters.BasicParameterDefinition;
import com.github.kmbulebu.nicknack.core.units.StringUnit;
import com.github.kmbulebu.nicknack.providers.xbmc.XbmcProvider;

public class TitleParameterDefinition extends BasicParameterDefinition<StringUnit> {
	
	private static final Logger logger = LogManager.getLogger(XbmcProvider.LOGGER_NAME);
	
	public static final UUID DEF_UUID = UUID.fromString("a43e0ab3-3bb4-4a5d-9b13-cdd9f8d20279");
	
	public static final TitleParameterDefinition INSTANCE = new TitleParameterDefinition();

	public TitleParameterDefinition() {
		super(DEF_UUID, "Title", StringUnit.INSTANCE, true);
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
