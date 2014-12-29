package com.github.kmbulebu.nicknack.providers.dsc.internal;

import com.github.kmbulebu.dsc.it100.commands.read.BaseZoneCommand;
import com.github.kmbulebu.dsc.it100.commands.read.ReadCommand;
import com.github.kmbulebu.dsc.it100.commands.read.ZoneOpenCommand;
import com.github.kmbulebu.dsc.it100.commands.read.ZoneRestoredCommand;

public class CommandToStateMapper {
	
	private final Zones zones;
	
	public CommandToStateMapper(Zones zones) {
		this.zones = zones;
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
		zone.setOpen(true);
	}

	protected void mapCommand(ZoneRestoredCommand command) {
		final Zone zone = getOrCreateZone(command);
		zone.setOpen(false);
	}
	
	protected Zone getOrCreateZone(BaseZoneCommand command) {
		return getOrCreateZone(command.getZone());
	}
	
	protected Zone getOrCreateZone(int zoneNumber) {
		Zone zone = zones.getZone(zoneNumber);
		if (zone == null) {
			zone = new Zone();
			zones.setZone(zoneNumber, zone);
		}
		return zone;
	}
}
