package com.github.kmbulebu.nicknack.providers.dsc.internal;

import java.util.Set;

import com.github.kmbulebu.dsc.it100.commands.read.BaseZoneCommand;
import com.github.kmbulebu.dsc.it100.commands.read.ReadCommand;
import com.github.kmbulebu.dsc.it100.commands.read.ZoneOpenCommand;
import com.github.kmbulebu.dsc.it100.commands.read.ZoneRestoredCommand;

public class CommandToStateMapper {
	
	private final Zones zones;
	private final Set<Integer> activeZones;
	
	public CommandToStateMapper(Zones zones, Set<Integer> activeZones) {
		this.zones = zones;
		this.activeZones = activeZones;
	}
	
	public void map(ReadCommand readCommand) {
		if (readCommand instanceof ZoneOpenCommand) {
			mapCommand((ZoneOpenCommand) readCommand);
		} else if (readCommand instanceof ZoneRestoredCommand) {
			mapCommand((ZoneRestoredCommand) readCommand);
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
