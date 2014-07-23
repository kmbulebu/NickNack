package com.oakcity.nicknack.core;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import com.oakcity.nicknack.core.events.AttributeDefinition;
import com.oakcity.nicknack.core.events.EventDefinition;
import com.oakcity.nicknack.core.units.BooleanUnit;

public class SwitchChangeEvent implements EventDefinition {
	
	private final UUID uuid = UUID.fromString("320c68e0-d662-11e3-9c1a-0800200c9a66");

	@Override
	public UUID getUUID() {
		return uuid;
	}

	@Override
	public String getName() {
		return "Switch Changed";
	}

	@Override
	public List<AttributeDefinition> getAttributeDefinitions() {
		return Collections.singletonList((AttributeDefinition) new SwitchPositionAttribute());
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SwitchChangeEvent other = (SwitchChangeEvent) obj;
		if (uuid == null) {
			if (other.uuid != null)
				return false;
		} else if (!uuid.equals(other.uuid))
			return false;
		return true;
	}

	public static class SwitchPositionAttribute implements AttributeDefinition {
		
		private final UUID uuid = UUID.fromString("320c68e0-d662-11e3-9c1a-0800200d9a66");
		
		@Override
		public boolean isOptional() {
			return false;
		}
		
		@Override
		public BooleanUnit getUnits() {
			return new BooleanUnit();
		}
		
		@Override
		public UUID getUUID() {
			return uuid;
		}
		
		@Override
		public String getName() {
			return "position";
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			SwitchPositionAttribute other = (SwitchPositionAttribute) obj;
			if (uuid == null) {
				if (other.uuid != null)
					return false;
			} else if (!uuid.equals(other.uuid))
				return false;
			return true;
		}
		
		
		
	}
	
	
}
