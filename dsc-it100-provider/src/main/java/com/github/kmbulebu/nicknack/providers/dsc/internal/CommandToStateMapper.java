package com.github.kmbulebu.nicknack.providers.dsc.internal;

import java.util.Set;

import com.github.kmbulebu.dsc.it100.commands.read.BasePartitionCommand;
import com.github.kmbulebu.dsc.it100.commands.read.BasePartitionZoneCommand;
import com.github.kmbulebu.dsc.it100.commands.read.BaseZoneCommand;
import com.github.kmbulebu.dsc.it100.commands.read.PartitionArmedCommand;
import com.github.kmbulebu.dsc.it100.commands.read.PartitionDisarmedCommand;
import com.github.kmbulebu.dsc.it100.commands.read.ReadCommand;
import com.github.kmbulebu.dsc.it100.commands.read.ZoneOpenCommand;
import com.github.kmbulebu.dsc.it100.commands.read.ZoneRestoredCommand;

public class CommandToStateMapper {
	
	private final Zones zones;
	private final Partitions partitions;
	private final Set<Integer> activeZones;
	private final Set<Integer> activePartitions;
	
	public CommandToStateMapper(Zones zones, Partitions partitions, Set<Integer> activeZones, Set<Integer> activePartitions) {
		this.zones = zones;
		this.partitions = partitions;
		this.activeZones = activeZones;
		this.activePartitions = activePartitions;
	}
	
	public void map(ReadCommand readCommand) {
		if (readCommand instanceof ZoneOpenCommand) {
			mapCommand((ZoneOpenCommand) readCommand);
		} else if (readCommand instanceof ZoneRestoredCommand) {
			mapCommand((ZoneRestoredCommand) readCommand);
		} else if (readCommand instanceof PartitionDisarmedCommand) {
			mapCommand((PartitionDisarmedCommand) readCommand);
		} else if (readCommand instanceof PartitionArmedCommand) {
			mapCommand((PartitionArmedCommand) readCommand);
		}
	}
	
	protected void mapCommand(ZoneOpenCommand command) {
		final Zone zone = getOrCreateZone(command);
		if (zone != null) {
			zone.setOpen(true);
		}
	}

	protected void mapCommand(ZoneRestoredCommand command) {
		final Zone zone = getOrCreateZone(command);
		if (zone != null) {
			zone.setOpen(false);
		}
	}
	
	protected void mapCommand(PartitionDisarmedCommand command) {
		final Partition partition = getOrCreatePartition(command);
		if (partition != null) {
			partition.setArmed(false);
			partition.setArmedMode("Disarmed");
		}
	}
	
	protected void mapCommand(PartitionArmedCommand command) {
		final Partition partition = getOrCreatePartition(command);
		if (partition != null) {
			partition.setArmed(true);
			partition.setArmedMode(command.getMode().getDescription());
		}
	}

	
	protected Partition getOrCreatePartition(BasePartitionCommand command) {
		return getOrCreatePartition(command.getPartition());
	}
	
	protected Partition getOrCreatePartition(int partitionNumber) {
		if (activePartitions != null && !activePartitions.contains(partitionNumber)) {
			return null;
		}
		Partition partition = partitions.getPartition(partitionNumber);
		if (partition == null) {
			partition = new Partition();
			partitions.setPartition(partitionNumber, partition);
		}
		return partition;
	}
	
	protected Partition getOrCreatePartition(BasePartitionZoneCommand command) {
		return getOrCreatePartition(command.getPartition());
	}
	
	protected Zone getOrCreateZone(BaseZoneCommand command) {
		return getOrCreateZone(command.getZone());
	}
	
	protected Zone getOrCreateZone(int zoneNumber) {
		if (activeZones != null && !activeZones.contains(zoneNumber)) {
			return null;
		}
		Zone zone = zones.getZone(zoneNumber);
		if (zone == null) {
			zone = new Zone();
			zones.setZone(zoneNumber, zone);
		}
		return zone;
	}
}
