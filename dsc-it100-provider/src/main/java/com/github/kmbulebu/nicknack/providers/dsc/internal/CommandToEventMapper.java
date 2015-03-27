package com.github.kmbulebu.nicknack.providers.dsc.internal;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.github.kmbulebu.dsc.it100.Labels;
import com.github.kmbulebu.dsc.it100.commands.read.BasePartitionCommand;
import com.github.kmbulebu.dsc.it100.commands.read.BasePartitionZoneCommand;
import com.github.kmbulebu.dsc.it100.commands.read.BaseZoneCommand;
import com.github.kmbulebu.dsc.it100.commands.read.EntryDelayInProgressCommand;
import com.github.kmbulebu.dsc.it100.commands.read.ExitDelayInProgressCommand;
import com.github.kmbulebu.dsc.it100.commands.read.PartitionArmedCommand;
import com.github.kmbulebu.dsc.it100.commands.read.PartitionDisarmedCommand;
import com.github.kmbulebu.dsc.it100.commands.read.PartitionInAlarmCommand;
import com.github.kmbulebu.dsc.it100.commands.read.ReadCommand;
import com.github.kmbulebu.dsc.it100.commands.read.ZoneOpenCommand;
import com.github.kmbulebu.dsc.it100.commands.read.ZoneRestoredCommand;
import com.github.kmbulebu.nicknack.core.events.Event;
import com.github.kmbulebu.nicknack.core.events.EventDefinition;
import com.github.kmbulebu.nicknack.core.events.impl.BasicTimestampedEvent;
import com.github.kmbulebu.nicknack.providers.dsc.attributes.PartitionArmedModeAttributeDefinition;
import com.github.kmbulebu.nicknack.providers.dsc.attributes.PartitionLabelAttributeDefinition;
import com.github.kmbulebu.nicknack.providers.dsc.attributes.PartitionNumberAttributeDefinition;
import com.github.kmbulebu.nicknack.providers.dsc.attributes.ZoneLabelAttributeDefinition;
import com.github.kmbulebu.nicknack.providers.dsc.attributes.ZoneNumberAttributeDefinition;
import com.github.kmbulebu.nicknack.providers.dsc.attributes.ZoneOpenAttributeDefinition;
import com.github.kmbulebu.nicknack.providers.dsc.events.EntryDelayInProgressEventDefinition;
import com.github.kmbulebu.nicknack.providers.dsc.events.ExitDelayInProgressEventDefinition;
import com.github.kmbulebu.nicknack.providers.dsc.events.PartitionArmedEventDefinition;
import com.github.kmbulebu.nicknack.providers.dsc.events.PartitionDisarmedEventDefinition;
import com.github.kmbulebu.nicknack.providers.dsc.events.PartitionInAlarmEventDefinition;
import com.github.kmbulebu.nicknack.providers.dsc.events.ZoneOpenCloseEventDefinition;

public class CommandToEventMapper {
	
	private final Labels labels;
	private final Set<Integer> activeZones;
	@SuppressWarnings("unused") // We will
	private final Set<Integer> activePartitions;
	
	public CommandToEventMapper(Labels labels, Set<Integer> activeZones, Set<Integer> activePartitions) {
		this.labels = labels;
		this.activeZones = activeZones;
		this.activePartitions = activePartitions;
	}
	
	public Event map(ReadCommand readCommand) {
		if (readCommand instanceof BaseZoneCommand) {
			if (activeZones != null && !activeZones.contains(((BaseZoneCommand) readCommand).getZone())) {
				return null;
			}
		} else if (readCommand instanceof BasePartitionZoneCommand) {
			if (activeZones != null && !activeZones.contains(((BasePartitionZoneCommand) readCommand).getZone())) {
				return null;
			}
		}
		if (readCommand instanceof ZoneOpenCommand) {
			return toEvent((ZoneOpenCommand) readCommand);
		} else if (readCommand instanceof ZoneRestoredCommand) {
			return toEvent((ZoneRestoredCommand) readCommand);
		} else if (readCommand instanceof PartitionArmedCommand) {
			return toEvent((PartitionArmedCommand) readCommand);
		} else if (readCommand instanceof PartitionDisarmedCommand) {
			return toEvent((PartitionDisarmedCommand) readCommand);
		} else if (readCommand instanceof PartitionInAlarmCommand) {
			return toEvent((PartitionInAlarmCommand) readCommand);
		} else if (readCommand instanceof EntryDelayInProgressCommand) {
			return toEvent((EntryDelayInProgressCommand) readCommand);
		} else if (readCommand instanceof ExitDelayInProgressCommand) {
			return toEvent((ExitDelayInProgressCommand) readCommand);
		}
		
		return null;
	}
	
	private Event toEvent(EntryDelayInProgressCommand partitionCommand) {
		return buildEvent(getAttributes(partitionCommand), EntryDelayInProgressEventDefinition.INSTANCE);
	}
	
	private Event toEvent(ExitDelayInProgressCommand partitionCommand) {
		return buildEvent(getAttributes(partitionCommand), ExitDelayInProgressEventDefinition.INSTANCE);
	}

	private Event toEvent(PartitionInAlarmCommand partitionCommand) {
		return buildEvent(getAttributes(partitionCommand), PartitionInAlarmEventDefinition.INSTANCE);
	}

	protected Event toEvent(ZoneOpenCommand zoneCommand) {
		return buildEvent(getAttributes(zoneCommand, true), ZoneOpenCloseEventDefinition.INSTANCE);
	}
	
	protected Event toEvent(ZoneRestoredCommand zoneCommand) {		
		return buildEvent(getAttributes(zoneCommand, false), ZoneOpenCloseEventDefinition.INSTANCE);
	}
	
	private Event buildEvent(final Map<UUID, Object> attributes, final EventDefinition eventDefinition) {
		final BasicTimestampedEvent event = new BasicTimestampedEvent(eventDefinition);
		event.setAttributes(attributes);
		return event;
	}
	
	protected Map<UUID, Object> getAttributes(BaseZoneCommand zoneCommand, boolean isOpen) {
		final Map<UUID, Object> attributes = getAttributes(zoneCommand);
		
		attributes.put(ZoneOpenAttributeDefinition.INSTANCE.getUUID(), isOpen);
		
		return attributes;
	}
	
	protected Map<UUID, Object> getAttributes(BaseZoneCommand zoneCommand) {
		final Map<UUID, Object> attributes = new HashMap<>();
		
		final int zone = zoneCommand.getZone();
		
		attributes.put(ZoneNumberAttributeDefinition.INSTANCE.getUUID(), zone);
		final String label = labels.getZoneLabel(zone);
		
		if (label != null) {
			attributes.put(ZoneLabelAttributeDefinition.INSTANCE.getUUID(), label.replaceAll("\\s+", " ")); // Compress multiple spaces into one.
		}
		
		return attributes;
	}
	
	protected Event toEvent(PartitionArmedCommand partitionCommand) {
		final Map<UUID, Object> attributes = getAttributes(partitionCommand);
		
		attributes.put(PartitionArmedModeAttributeDefinition.INSTANCE.getUUID(), partitionCommand.getMode().getDescription());
		
		return buildEvent(attributes, PartitionArmedEventDefinition.INSTANCE);
	}
	
	protected Event toEvent(PartitionDisarmedCommand partitionCommand) {
		return buildEvent(getAttributes(partitionCommand), PartitionDisarmedEventDefinition.INSTANCE);
	}
	
	protected Map<UUID, Object> getAttributes(BasePartitionCommand partitionCommand) {
		final Map<UUID, Object> attributes = new HashMap<>();
		
		final int partition = partitionCommand.getPartition();
		
		attributes.put(PartitionNumberAttributeDefinition.INSTANCE.getUUID(), partition);
		final String label = labels.getPartitionLabel(partition);
		
		if (label != null) {
			attributes.put(PartitionLabelAttributeDefinition.INSTANCE.getUUID(), label.replaceAll("\\s+", " "));
		}
		
		return attributes;
	}

}
