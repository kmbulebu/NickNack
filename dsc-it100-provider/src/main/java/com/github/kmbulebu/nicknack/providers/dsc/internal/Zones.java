package com.github.kmbulebu.nicknack.providers.dsc.internal;

public class Zones {	
	
	private final Zone[] zones;
	
	public Zones() {
		zones = new Zone[128];
	}
	
	public Zone getZone(int zoneNumber) {
		// Zones start with 1, not zero.
		return zones[zoneNumber - 1];
	}
	
	public void setZone(int zoneNumber, Zone zone) {
		zones[zoneNumber - 1] = zone;
	}
	
}
