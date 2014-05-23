package com.oakcity.nicknack.example.provider;

import java.util.UUID;

import com.oakcity.nicknack.core.events.BasicAttributeDefinition;
import com.oakcity.nicknack.core.events.BasicEventDefinition;
import com.oakcity.nicknack.core.events.attributes.units.BooleanUnit;
import com.oakcity.nicknack.core.events.attributes.units.StringUnit;

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
	
	public SwitchChangeEventDefinition() {
		super(UUID.fromString("320c68e0-d662-11e3-9c1a-0800200c9a66"), 
				"Switch Changed", 
				new BasicAttributeDefinition(UUID.fromString("320c68e0-d662-11e3-9c1a-0800200d9a66"), "position", new BooleanUnit(), false),
				new BasicAttributeDefinition(UUID.fromString("920c68e0-d662-31e3-9c1a-0800200d9a66"), "macAddress", new StringUnit(), false));
	}
	
}
