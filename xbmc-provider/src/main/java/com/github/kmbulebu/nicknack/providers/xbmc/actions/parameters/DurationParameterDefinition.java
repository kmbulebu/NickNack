package com.github.kmbulebu.nicknack.providers.xbmc.actions.parameters;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.kmbulebu.nicknack.core.actions.parameters.BasicParameterDefinition;
import com.github.kmbulebu.nicknack.core.units.IntegerUnit;
import com.github.kmbulebu.nicknack.providers.xbmc.XbmcProvider;

public class DurationParameterDefinition extends BasicParameterDefinition<IntegerUnit> {
	
	private static final Logger logger = LogManager.getLogger(XbmcProvider.LOGGER_NAME);
	
	public static final UUID DEF_UUID = UUID.fromString("f9ffe599-6208-41f7-a6fa-124ec4c19576");
	
	public static final DurationParameterDefinition INSTANCE = new DurationParameterDefinition();

	public DurationParameterDefinition() {
		super(DEF_UUID, "Duration (ms)", IntegerUnit.INSTANCE, true);
	}

	@Override
	public String format(String rawValue) {
		return rawValue;
	}
	
	private static final String VALIDATE_ERROR = "Duration must be an integer value of 1500 or greater.";

	@Override
	public Collection<String> validate(String value) {
		if (logger.isTraceEnabled()) {
			logger.entry(value);
		}
		if (!value.matches("\\d+")) {
			return Collections.singletonList(VALIDATE_ERROR);
		}
		final int intValue = Integer.parseInt(value);
		
		if (intValue < 1500) {
			return Collections.singletonList(VALIDATE_ERROR);
		}
		return Collections.emptyList();
	}

}
