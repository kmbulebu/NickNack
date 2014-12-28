package com.github.kmbulebu.nicknack.example.provider;

import java.util.UUID;

import com.github.kmbulebu.nicknack.core.attributes.AttributeDefinition;
import com.github.kmbulebu.nicknack.core.attributes.BasicAttributeDefinition;
import com.github.kmbulebu.nicknack.core.events.BasicEventDefinition;
import com.github.kmbulebu.nicknack.core.units.BooleanUnit;
import com.github.kmbulebu.nicknack.core.units.StringUnit;

/**
 * Our 'switch' acts like a normal light switch. It's up or down (on or off) and is connected to a network.
 * 
 * 'position' attribute defines the current position of the switch (on or off).
 * 'macAddress' attribute uniquely identifies the switch on the network. 
 * 
 * @author kmbulebu
 *
 */
public class SwitchChangeEventDefinition extends BasicEventDefinition {
	
	public static AttributeDefinition POSITION_ATTRIBUTE_DEF = new BasicAttributeDefinition(UUID.fromString("320c68e0-d662-11e3-9c1a-0800200d9a66"), "position", new BooleanUnit(), false);
	public static AttributeDefinition MAC_ADDRESS_ATTRIBUTE_DEF = new BasicAttributeDefinition(UUID.fromString("920c68e0-d662-31e3-9c1a-0800200d9a66"), "macAddress", new StringUnit(), false);
	
	public static UUID INSTANCE_UUID = UUID.fromString("320c68e0-d662-11e3-9c1a-0800200c9a66");

	public static SwitchChangeEventDefinition INSTANCE = new SwitchChangeEventDefinition();
	
	public SwitchChangeEventDefinition() {
		super(INSTANCE_UUID, 
				"Switch Changed", 
				POSITION_ATTRIBUTE_DEF,
				MAC_ADDRESS_ATTRIBUTE_DEF);
	}
	
}
