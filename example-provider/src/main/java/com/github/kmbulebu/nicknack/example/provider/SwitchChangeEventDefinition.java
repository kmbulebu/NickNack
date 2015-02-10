package com.github.kmbulebu.nicknack.example.provider;

import java.util.UUID;

import com.github.kmbulebu.nicknack.core.attributes.AttributeDefinition;
import com.github.kmbulebu.nicknack.core.attributes.BasicAttributeDefinition;
import com.github.kmbulebu.nicknack.core.events.BasicEventDefinition;
import com.github.kmbulebu.nicknack.core.valuetypes.CheckboxType;
import com.github.kmbulebu.nicknack.core.valuetypes.TextType;

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
	
	public static AttributeDefinition<CheckboxType> POSITION_ATTRIBUTE_DEF = new BasicAttributeDefinition<CheckboxType>(UUID.fromString("320c68e0-d662-11e3-9c1a-0800200d9a66"), "position", new CheckboxType(), false);
	public static AttributeDefinition<TextType> MAC_ADDRESS_ATTRIBUTE_DEF = new BasicAttributeDefinition<TextType>(UUID.fromString("920c68e0-d662-31e3-9c1a-0800200d9a66"), "macAddress", new TextType(), false);
	
	public static UUID INSTANCE_UUID = UUID.fromString("320c68e0-d662-11e3-9c1a-0800200c9a66");

	public static SwitchChangeEventDefinition INSTANCE = new SwitchChangeEventDefinition();
	
	public SwitchChangeEventDefinition() {
		super(INSTANCE_UUID, 
				"Switch Changed", 
				POSITION_ATTRIBUTE_DEF,
				MAC_ADDRESS_ATTRIBUTE_DEF);
	}
	
}
