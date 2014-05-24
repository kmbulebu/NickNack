package com.oakcity.nicknack.example.provider;

import java.util.UUID;

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

	public LightBulbActionDefinition() {
		super(UUID.fromString("d30f0828-e356-11e3-918b-f365e95fde44"), 
				"Control Light Bulb", 
				new BasicParameterDefinition(UUID.fromString("e87cfada-e356-11e3-a8a4-c7fd038daf35"), "switch", new BooleanUnit(), true),
				new BasicParameterDefinition(UUID.fromString("920c68e0-d662-31e3-9c1a-0800200d9a66"), "macAddress", new StringUnit(), true));
	}

}
