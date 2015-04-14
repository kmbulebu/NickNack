package com.github.kmbulebu.nicknack.server.services.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.github.kmbulebu.nicknack.core.providers.Provider;
import com.github.kmbulebu.nicknack.server.restmodel.Attribute;
import com.github.kmbulebu.nicknack.server.restmodel.State;
import com.github.kmbulebu.nicknack.server.restmodel.StateDefinition;
import com.github.kmbulebu.nicknack.server.restmodel.impl.StateImpl;
import com.github.kmbulebu.nicknack.server.services.CoreProviderServiceWrapper;
import com.github.kmbulebu.nicknack.server.services.StateDefinitionsService;
import com.github.kmbulebu.nicknack.server.services.StatesService;

@Service
public class StatesServiceImpl implements StatesService {
	
	@Inject
	private StateDefinitionsService stateDefinitionsService;
	
	@Inject
	private CoreProviderServiceWrapper coreProviderService;
	
	@Inject
	private AttributeMapper attributeMapper;

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
			states.addAll(getStates(stateDefinition, providerUuid));
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
	
		final List<State> states = getStates(definition, providerUuid);
		
		return Collections.unmodifiableList(states);
	}
	
	protected List<State> getStates(StateDefinition stateDefinition, UUID providerUuid) {
		
		final List<com.github.kmbulebu.nicknack.core.states.State> coreStates = coreProviderService.getNickNackProviderService().getStates(stateDefinition.getUuid());
		final List<State> states = new ArrayList<>(coreStates.size());
		
		for (com.github.kmbulebu.nicknack.core.states.State coreState : coreStates) {
			final State state = mapState(coreState, stateDefinition, providerUuid);
			states.add(state);
		}
		
		return states;
	}
	
	public State mapState(com.github.kmbulebu.nicknack.core.states.State coreState, StateDefinition definition, UUID providerUuid) {
		
		
		final StateImpl state = new StateImpl(definition);
		final List<Attribute> attributes = new ArrayList<>(coreState.getAttributes().size());
		
		for (UUID attributeUuid : coreState.getAttributes().keySet()) {
			// Find attribute definition
			final com.github.kmbulebu.nicknack.core.attributes.AttributeDefinition<?,?> coreAttributeDefinition = coreProviderService.getNickNackProviderService().getAttributeDefinition(attributeUuid);
			
			if (coreAttributeDefinition == null) {
				continue;
			}
			
			final Object value = coreState.getAttributes().get(attributeUuid);
			
			// Map the attribute 
			final Attribute attribute = attributeMapper.mapFromSingleValue(coreAttributeDefinition, providerUuid, value);
			
			attributes.add(attribute);			
		}
		
		state.setAttributes(attributes);
		
		return state;
	}

}
