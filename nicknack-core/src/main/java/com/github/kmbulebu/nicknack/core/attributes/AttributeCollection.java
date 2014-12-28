package com.github.kmbulebu.nicknack.core.attributes;

import java.util.Map;
import java.util.UUID;

public interface AttributeCollection {
	
	// Attribute definition to Attribute Value map.
	public Map<UUID, String> getAttributes();

}
