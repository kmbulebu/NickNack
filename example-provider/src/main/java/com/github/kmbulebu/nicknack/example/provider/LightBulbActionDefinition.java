package com.github.kmbulebu.nicknack.example.provider;

import java.util.UUID;

import com.github.kmbulebu.nicknack.core.actions.BasicActionDefinition;
import com.github.kmbulebu.nicknack.core.attributes.BasicAttributeDefinition;
import com.github.kmbulebu.nicknack.core.attributes.impl.MacAddressAttributeDefinition;
import com.github.kmbulebu.nicknack.core.attributes.impl.OnOffSwitchAttributeDefinition;
import com.github.kmbulebu.nicknack.core.valuetypes.CheckboxType;
import com.github.kmbulebu.nicknack.core.valuetypes.TextType;

/**
 * A network connected lightbulb that can be switched on or off.
 * 
 * 'switch' parameter defines the new position of the light bulb power switch (on or off).
 * 'macAddress' parameter uniquely identifies the light bulb on the network. 
 * @author kmbulebu
 *
 */
public class LightBulbActionDefinition extends BasicActionDefinition {
	
	public static BasicAttributeDefinition<CheckboxType> SWITCH_PARAMETER_DEFINITION = new OnOffSwitchAttributeDefinition(UUID.fromString("e87cfada-e356-11e3-a8a4-c7fd038daf35"), "switch", true);
	public static BasicAttributeDefinition<TextType> MAC_ADDRESS_PARAMETER_DEFINITION = new MacAddressAttributeDefinition(UUID.fromString("920c68e0-d662-31e3-9c1a-0800200d9a66"), "macAddress", true);

	public LightBulbActionDefinition() {
		super(UUID.fromString("d30f0828-e356-11e3-918b-f365e95fde44"), 
				"Control Light Bulb", 
				"Turns a light bulb on or off",
				SWITCH_PARAMETER_DEFINITION,
				MAC_ADDRESS_PARAMETER_DEFINITION);
	}

}
