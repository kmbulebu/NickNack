package com.oakcity.nicknack.server.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.oakcity.nicknack.core.events.BasicAttributeDefinition;
import com.oakcity.nicknack.core.events.BasicEventDefinition;
import com.oakcity.nicknack.core.events.Event.EventDefinition;
import com.oakcity.nicknack.core.events.attributes.units.BooleanUnit;
import com.oakcity.nicknack.core.events.attributes.units.StringUnit;
import com.oakcity.nicknack.server.services.EventDefinitionService;


@Service
public class EventDefinitionServiceImpl implements EventDefinitionService {

	@Override
	public List<EventDefinition> getEventDefinitions() {
		List<EventDefinition> defs = new ArrayList<EventDefinition>();
		defs.add(getEventDefinition(UUID.randomUUID()));
		defs.add(getEventDefinition(UUID.randomUUID()));
		defs.add(getEventDefinition(UUID.randomUUID()));
		return defs;
	}

	@Override
	public EventDefinition getEventDefinition(final UUID uuid) {
		return new BasicEventDefinition(uuid, 
				"Switch Changed", 
				new BasicAttributeDefinition(UUID.fromString("320c68e0-d662-11e3-9c1a-0800200d9a66"), "position", new BooleanUnit(), false),
				new BasicAttributeDefinition(UUID.fromString("920c68e0-d662-31e3-9c1a-0800200d9a66"), "macAddress", new StringUnit(), false));
	}

}
