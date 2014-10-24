package com.github.kmbulebu.nicknack.example.provider;

import java.util.UUID;

import com.github.kmbulebu.nicknack.core.actions.BasicActionDefinition;
import com.github.kmbulebu.nicknack.core.actions.parameters.BasicParameterDefinition;
import com.github.kmbulebu.nicknack.core.actions.parameters.MacAddressParameterDefinition;
import com.github.kmbulebu.nicknack.core.actions.parameters.OnOffSwitchParameterDefinition;
import com.github.kmbulebu.nicknack.core.units.BooleanUnit;
import com.github.kmbulebu.nicknack.core.units.StringUnit;

/**
 * A network connected lightbulb that can be switched on or off.
 * 
 * 'switch' parameter defines the new position of the light bulb power switch (on or off).
 * 'macAddress' parameter uniquely identifies the light bulb on the network. 
 * @author kmbulebu
 *
 */
public class LightBulbActionDefinition extends BasicActionDefinition {
	
	public static BasicParameterDefinition<BooleanUnit> SWITCH_PARAMETER_DEFINITION = new OnOffSwitchParameterDefinition(UUID.fromString("e87cfada-e356-11e3-a8a4-c7fd038daf35"), "switch", true);
	public static BasicParameterDefinition<StringUnit> MAC_ADDRESS_PARAMETER_DEFINITION = new MacAddressParameterDefinition(UUID.fromString("920c68e0-d662-31e3-9c1a-0800200d9a66"), "macAddress", true);

	public LightBulbActionDefinition() {
		super(UUID.fromString("d30f0828-e356-11e3-918b-f365e95fde44"), ExampleProvider.PROVIDER_UUID,
				"Control Light Bulb", 
				SWITCH_PARAMETER_DEFINITION,
				MAC_ADDRESS_PARAMETER_DEFINITION);
	}

}
