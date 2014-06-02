package com.oakcity.nicknack.example.provider;

import java.util.UUID;

import com.oakcity.nicknack.core.actions.Action;
import com.oakcity.nicknack.core.actions.BasicActionDefinition;
import com.oakcity.nicknack.core.actions.BasicParameterDefinition;
import com.oakcity.nicknack.core.events.attributes.units.BooleanUnit;
import com.oakcity.nicknack.core.events.attributes.units.StringUnit;

/**
 * A network connected lightbulb that can be switched on or off.
 * 
 * 'switch' parameter defines the new position of the light bulb power switch (on or off).
 * 'macAddress' parameter uniquely identifies the light bulb on the network. 
 * @author kmbulebu
 *
 */
public class LightBulbActionDefinition extends BasicActionDefinition {
	
	public static BasicParameterDefinition<BooleanUnit> SWITCH_PARAMETER_DEFINITION = new BasicParameterDefinition<BooleanUnit>(UUID.fromString("e87cfada-e356-11e3-a8a4-c7fd038daf35"), "switch", new BooleanUnit(), true);
	public static BasicParameterDefinition<StringUnit> MAC_ADDRESS_PARAMETER_DEFINITION = new BasicParameterDefinition<StringUnit>(UUID.fromString("920c68e0-d662-31e3-9c1a-0800200d9a66"), "macAddress", new StringUnit(), true);

	public LightBulbActionDefinition() {
		super(UUID.fromString("d30f0828-e356-11e3-918b-f365e95fde44"), ExampleProvider.PROVIDER_UUID,
				"Control Light Bulb", 
				SWITCH_PARAMETER_DEFINITION,
				MAC_ADDRESS_PARAMETER_DEFINITION);
	}

	@Override
	public void run(Action action) {
		String macAddress = action.getParameters().get(MAC_ADDRESS_PARAMETER_DEFINITION.getUUID());
		
		String switchStr = action.getParameters().get(SWITCH_PARAMETER_DEFINITION.getUUID()); 
		
		if (switchStr == null) {
			// TODO Use our own exception
			throw new IllegalArgumentException(SWITCH_PARAMETER_DEFINITION.getName() + " is required.");
		}
		
		if (macAddress == null) {
			// TODO Use our own exception
			throw new IllegalArgumentException(MAC_ADDRESS_PARAMETER_DEFINITION.getName() + " is required.");
		}
		
		Boolean switchBool = SWITCH_PARAMETER_DEFINITION.getUnits().parse(switchStr);
		
		// Our dummy action
		System.out.println("Switch changing to " + switchBool);
		
	}

}
