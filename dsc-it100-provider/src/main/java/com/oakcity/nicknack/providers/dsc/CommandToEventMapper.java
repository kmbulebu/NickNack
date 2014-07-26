package com.oakcity.nicknack.providers.dsc;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.oakcity.dsc.it100.Labels;
import com.oakcity.dsc.it100.commands.read.BasePartitionCommand;
import com.oakcity.dsc.it100.commands.read.BaseZoneCommand;
import com.oakcity.dsc.it100.commands.read.PartitionArmedCommand;
import com.oakcity.dsc.it100.commands.read.PartitionDisarmedCommand;
import com.oakcity.dsc.it100.commands.read.ReadCommand;
import com.oakcity.dsc.it100.commands.read.ZoneOpenCommand;
import com.oakcity.dsc.it100.commands.read.ZoneRestoredCommand;
import com.oakcity.nicknack.core.events.Event;
import com.oakcity.nicknack.core.events.EventDefinition;
import com.oakcity.nicknack.providers.dsc.events.PartitionArmedEventDefinition;
import com.oakcity.nicknack.providers.dsc.events.PartitionArmedModeAttributeDefinition;
import com.oakcity.nicknack.providers.dsc.events.PartitionDisarmedEventDefinition;
import com.oakcity.nicknack.providers.dsc.events.PartitionLabelAttributeDefinition;
import com.oakcity.nicknack.providers.dsc.events.PartitionNumberAttributeDefinition;
import com.oakcity.nicknack.providers.dsc.events.ZoneLabelAttributeDefinition;
import com.oakcity.nicknack.providers.dsc.events.ZoneNumberAttributeDefinition;
import com.oakcity.nicknack.providers.dsc.events.ZoneOpenAttributeDefinition;
import com.oakcity.nicknack.providers.dsc.events.ZoneOpenCloseEventDefinition;

public class CommandToEventMapper {
	
	private final Labels labels;
	
	public CommandToEventMapper(Labels labels) {
		this.labels = labels;
	}
	
	public Event map(ReadCommand readCommand) {
		if (readCommand instanceof ZoneOpenCommand) {
			return toEvent((ZoneOpenCommand) readCommand);
		} else if (readCommand instanceof ZoneRestoredCommand) {
			return toEvent((ZoneRestoredCommand) readCommand);
		} else if (readCommand instanceof PartitionArmedCommand) {
			return toEvent((PartitionArmedCommand) readCommand);
		} else if (readCommand instanceof PartitionDisarmedCommand) {
			return toEvent((PartitionDisarmedCommand) readCommand);
		}
		
		return null;
	}
	
	protected Event toEvent(ZoneOpenCommand zoneCommand) {
		return buildEvent(getAttributes(zoneCommand, true), ZoneOpenCloseEventDefinition.INSTANCE);
	}
	
	protected Event toEvent(ZoneRestoredCommand zoneCommand) {		
		return buildEvent(getAttributes(zoneCommand, false), ZoneOpenCloseEventDefinition.INSTANCE);
	}
	
	private Event buildEvent(final Map<UUID, String> attributes, final EventDefinition eventDefinition) {
		final Map<UUID, String> unmodifiableMap = Collections.unmodifiableMap(attributes);
		final Event event = new Event() {

			@Override
			public Map<UUID, String> getAttributes() {
				return unmodifiableMap;
			}

			@Override
			public EventDefinition getEventDefinition() {
				return eventDefinition;
			}
			
		};
		
		return event;
	}
	
	protected Map<UUID, String> getAttributes(BaseZoneCommand zoneCommand, boolean isOpen) {
		final Map<UUID, String> attributes = getAttributes(zoneCommand);
		
		attributes.put(ZoneOpenAttributeDefinition.INSTANCE.getUUID(), Boolean.toString(isOpen));
		
		return attributes;
	}
	
	protected Map<UUID, String> getAttributes(BaseZoneCommand zoneCommand) {
		final Map<UUID, String> attributes = new HashMap<>();
		
		final int zone = zoneCommand.getZone();
		
		attributes.put(ZoneNumberAttributeDefinition.INSTANCE.getUUID(), Integer.toString(zone));
		final String label = labels.getZoneLabel(zone);
		
		if (label != null) {
			attributes.put(ZoneLabelAttributeDefinition.INSTANCE.getUUID(), label);
		}
		
		return attributes;
	}
	
	protected Event toEvent(PartitionArmedCommand partitionCommand) {
		final Map<UUID, String> attributes = getAttributes(partitionCommand);
		
		attributes.put(PartitionArmedModeAttributeDefinition.INSTANCE.getUUID(), partitionCommand.getMode().getDescription());
		
		return buildEvent(attributes, PartitionArmedEventDefinition.INSTANCE);
	}
	
	protected Event toEvent(PartitionDisarmedCommand partitionCommand) {
		return buildEvent(getAttributes(partitionCommand), PartitionDisarmedEventDefinition.INSTANCE);
	}
	
	protected Map<UUID, String> getAttributes(BasePartitionCommand partitionCommand) {
		final Map<UUID, String> attributes = new HashMap<>();
		
		final int partition = partitionCommand.getPartition();
		
		attributes.put(PartitionNumberAttributeDefinition.INSTANCE.getUUID(), Integer.toString(partition));
		final String label = labels.getPartitionLabel(partition);
		
		if (label != null) {
			attributes.put(PartitionLabelAttributeDefinition.INSTANCE.getUUID(), label);
		}
		
		return attributes;
	}

}
