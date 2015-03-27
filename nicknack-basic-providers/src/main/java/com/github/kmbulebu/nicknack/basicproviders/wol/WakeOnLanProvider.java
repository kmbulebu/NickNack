package com.github.kmbulebu.nicknack.basicproviders.wol;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import com.github.kmbulebu.nicknack.core.attributes.AttributeCollection;
import com.github.kmbulebu.nicknack.core.providers.BadConfigurationException;
import com.github.kmbulebu.nicknack.core.providers.BaseProvider;
import com.github.kmbulebu.nicknack.core.providers.OnEventListener;
import com.github.kmbulebu.nicknack.core.providers.ProviderFailureException;
import com.github.kmbulebu.nicknack.core.states.State;
import com.github.kmbulebu.nicknack.core.states.StateDefinition;

/**
 * Provides real time clock capabilities to Nick Nack.
 * @author kmbulebu
 *
 */
public class WakeOnLanProvider extends BaseProvider {
	
	public static final UUID PROVIDER_UUID = UUID.fromString("16f5774b-8104-4a29-85d7-e6d02d6353d2");

	public WakeOnLanProvider() {
		super(PROVIDER_UUID, "WakeOnLan", "NickNack", 1);
	}
	
	@Override
	public void init(AttributeCollection settings, OnEventListener onEventListener) throws BadConfigurationException, ProviderFailureException {
		super.init(settings, onEventListener);
		
		addActionDefinition(WakeOnLanActionDefinition.INSTANCE);
	}
		
	@Override
	protected List<State> getStates(StateDefinition stateDefinition) {
		return Collections.emptyList();
	}

}
