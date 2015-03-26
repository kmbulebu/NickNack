package com.github.kmbulebu.nicknack.server.services;

import com.github.kmbulebu.nicknack.server.dbmodel.ProviderEntity;
import com.github.kmbulebu.nicknack.server.restmodel.Provider;

public interface ProviderMapper {

	public Provider map(com.github.kmbulebu.nicknack.core.providers.Provider provider, ProviderEntity dbProviderEntity);

}