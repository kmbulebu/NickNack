package com.github.kmbulebu.nicknack.server.services;

import java.util.List;

import com.github.kmbulebu.nicknack.core.attributes.AttributeValueParser.InvalidValueException;
import com.github.kmbulebu.nicknack.core.valuetypes.ValueType.ParseException;
import com.github.kmbulebu.nicknack.server.exceptions.EntityDoesNotExist;
import com.github.kmbulebu.nicknack.server.restmodel.Provider;

public interface ProvidersService {

	public List<Provider> getAllProviders();

	public void updateSettings(Provider provider) throws ParseException, InvalidValueException, EntityDoesNotExist;

}