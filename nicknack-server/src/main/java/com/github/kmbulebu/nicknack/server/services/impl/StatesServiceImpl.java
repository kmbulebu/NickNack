package com.github.kmbulebu.nicknack.server.services.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.github.kmbulebu.nicknack.core.attributes.AttributeValueParser;
import com.github.kmbulebu.nicknack.core.providers.Provider;
import com.github.kmbulebu.nicknack.server.restmodel.State;
import com.github.kmbulebu.nicknack.server.restmodel.StateDefinition;
import com.github.kmbulebu.nicknack.server.services.CoreProviderServiceWrapper;
import com.github.kmbulebu.nicknack.server.services.StateDefinitionsService;
import com.github.kmbulebu.nicknack.server.services.StatesService;

@Service
public class StatesServiceImpl implements StatesService {
	
	@Inject
	private StateDefinitionsService stateDefinitionsService;
	
	@Inject
	private CoreProviderServiceWrapper coreProviderService;
	
	private AttributeValueParser valueParser = new AttributeValueParser();

	@Override
	public List<State> getAllStates() {
		final List<State> states = new LinkedList<>();
		for (UUID providerUuid : coreProviderService.getNickNackProviderService().getProviders().keySet()) {
			states.addAll(getStatesByProvider(providerUuid));
		}
		return Collections.unmodifiableList(states);
	}

	@Override
	public List<State> getStatesByProvider(UUID providerUuid) {
		final List<StateDefinition> stateDefinitions = stateDefinitionsService.getStateDefinitionsByProvider(providerUuid);
		
		final List<State> states = new LinkedList<>();
		
		for (StateDefinition stateDefinition : stateDefinitions) {
			states.addAll(getStates(stateDefinition.getUuid(), providerUuid));
		}
	
		return Collections.unmodifiableList(states);
	}

	@Override
	public List<State> getStatesByStateDefinition(UUID uuid) {
		final StateDefinition definition = stateDefinitionsService.getStateDefinition(uuid);
		
		if (definition == null) {
			return Collections.emptyList();
		}
		
		final Provider provider = coreProviderService.getNickNackProviderService().getProviderByStateDefinitionUuid(uuid);
		
		if (provider == null) {
			return Collections.emptyList();
		}
		
		final UUID providerUuid = provider.getUuid();
	
		final List<State> states = getStates(uuid, providerUuid);
		
		return Collections.unmodifiableList(states);
	}
	
	protected List<State> getStates(UUID stateDefinitionUuid, UUID providerUuid) {
		
		final List<com.github.kmbulebu.nicknack.core.states.State> coreStates = coreProviderService.getNickNackProviderService().getStates(stateDefinitionUuid);
		final List<State> states = new ArrayList<>(coreStates.size());
		
		for (com.github.kmbulebu.nicknack.core.states.State coreState : coreStates) {
			final State state = mapState(coreState, providerUuid);
			states.add(state);
		}
		
		return states;
	}
	
	public State mapState(com.github.kmbulebu.nicknack.core.states.State coreState, UUID providerUuid) {
		
		
		final State state = new State();
		state.setUuid(coreState.getStateDefinition().getUUID());

		final Map<UUID, String[]> attributeValues = new HashMap<>();
		
		for (UUID attributeUuid : coreState.getAttributes().keySet()) {
			// Find attribute definition
			final com.github.kmbulebu.nicknack.core.attributes.AttributeDefinition<?,?> coreAttributeDefinition = coreProviderService.getNickNackProviderService().getAttributeDefinition(attributeUuid);
			
			if (coreAttributeDefinition == null) {
				continue;
			}
			
			final Object value = coreState.getAttributes().get(attributeUuid);
			
			final String strValue = valueParser.toString(coreAttributeDefinition, value);
			
			attributeValues.put(coreAttributeDefinition.getUUID(), new String[] {strValue});
				
		}
		
		state.setAttributes(attributeValues);
		
		return state;
	}

}
