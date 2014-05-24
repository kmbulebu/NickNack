package com.oakcity.nicknack.server.services.impl;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.oakcity.nicknack.core.events.Event.AttributeDefinition;
import com.oakcity.nicknack.core.events.Event.EventDefinition;
import com.oakcity.nicknack.server.services.EventDefinitionService;


@Service
public class EventDefinitionServiceImpl implements EventDefinitionService {

	@Override
	public List<EventDefinition> getEventDefinitions() {
		return Collections.singletonList(getEventDefinition(UUID.randomUUID()));
	}

	@Override
	public EventDefinition getEventDefinition(final UUID uuid) {
		return new EventDefinition() {
			
			@Override
			public UUID getUUID() {
				return uuid;
			}
			
			@Override
			public String getName() {
				return "Dummy";
			}
			
			@Override
			public List<AttributeDefinition> getAttributeDefinitions() {
				return Collections.emptyList();
			}
		};
	}


}
