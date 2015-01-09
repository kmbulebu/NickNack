package com.github.kmbulebu.nicknack.basicproviders.wol;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.github.kmbulebu.nicknack.basicproviders.wol.WakeOnLanActionDefinition.DeviceMacAddressParameterDefinition;
import com.github.kmbulebu.nicknack.core.actions.Action;
import com.github.kmbulebu.nicknack.core.actions.ActionDefinition;
import com.github.kmbulebu.nicknack.core.actions.ActionFailureException;
import com.github.kmbulebu.nicknack.core.actions.ActionParameterException;
import com.github.kmbulebu.nicknack.core.events.EventDefinition;
import com.github.kmbulebu.nicknack.core.providers.OnEventListener;
import com.github.kmbulebu.nicknack.core.providers.Provider;
import com.github.kmbulebu.nicknack.core.providers.ProviderConfiguration;
import com.github.kmbulebu.nicknack.core.providers.settings.ProviderSettingDefinition;
import com.github.kmbulebu.nicknack.core.states.State;
import com.github.kmbulebu.nicknack.core.states.StateDefinition;

/**
 * Provides real time clock capabilities to Nick Nack.
 * @author kmbulebu
 *
 */
public class WakeOnLanProvider implements Provider {
	
	public static final UUID PROVIDER_UUID = UUID.fromString("16f5774b-8104-4a29-85d7-e6d02d6353d2");
	
	private List<EventDefinition> eventDefinitions;
	private List<ActionDefinition> actionDefinitions;
	private List<StateDefinition> stateDefinitions;
	
	@Override
	public UUID getUuid() {
		return PROVIDER_UUID;
	}
	@Override
	public String getName() {
		return "WakeOnLan";
	}
	@Override
	public String getAuthor() {
		return "NickNack";
	}
	@Override
	public int getVersion() {
		return 1;
	}
	
	@Override
	public List<EventDefinition> getEventDefinitions() {
		return Collections.unmodifiableList(eventDefinitions);
	}
	
	@Override
	public Collection<StateDefinition> getStateDefinitions() {
		return Collections.unmodifiableList(stateDefinitions);
	}
	
	@Override
	public List<ActionDefinition> getActionDefinitions() {
		return Collections.unmodifiableList(actionDefinitions);
	}
	
	@Override
	public void init(ProviderConfiguration configuration, OnEventListener onEventListener) throws Exception {
		eventDefinitions = new ArrayList<>(0);
		actionDefinitions = new ArrayList<>(1);
		stateDefinitions = new ArrayList<>(0);
		actionDefinitions.add(WakeOnLanActionDefinition.INSTANCE);

	}

	@Override
	public Map<String, String> getAttributeDefinitionValues(UUID attributeDefinitionUuid) {
		return null;
	}
	
	@Override
	public void run(Action action) throws ActionParameterException, ActionFailureException {
		if (WakeOnLanActionDefinition.WOL_ACTION_UUID.equals(action.getAppliesToActionDefinition())) {
			if (action.getAttributes() == null) {
				throw new ActionParameterException("Parameters are required.");
			}
			
			final String macAddress = action.getAttributes().get(DeviceMacAddressParameterDefinition.MAC_ADDRESS_PARAMETER_UUID);
			if (macAddress == null) {
				throw new ActionParameterException(DeviceMacAddressParameterDefinition.INSTANCE.getName() + " is required.");
			}
			
			final WakeOnLan wol = new WakeOnLan(macAddress);
			
			try {
				wol.send();
			} catch (IOException e) {
				throw new ActionFailureException(e);
			}
		}
	}
	
	@Override
	public List<State> getStates(UUID stateDefinitionUuid) {
		return Collections.emptyList();
	}

	@Override
	public List<? extends ProviderSettingDefinition<?>> getSettingDefinitions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void shutdown() throws Exception {
		stateDefinitions = null;
		eventDefinitions = null;
		actionDefinitions = null;
	}

}
