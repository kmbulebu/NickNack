package com.oakcity.nicknack.basicproviders.clock;

import java.util.UUID;

import com.oakcity.nicknack.core.events.AttributeDefinition;
import com.oakcity.nicknack.core.events.BasicAttributeDefinition;
import com.oakcity.nicknack.core.events.BasicEventDefinition;
import com.oakcity.nicknack.core.units.DateTimeUnit;

/**
 * Creates a new 'Time Changed' event every second, nearest to the top of the second as possible.
 * 
 * 'dateTime' attribute defines the date and time the clock tick event was generated.
 * 
 * @author kmbulebu
 *
 */
public class ClockTickEventDefinition extends BasicEventDefinition {
	
	public static AttributeDefinition DATETIME_ATTRIBUTE_DEF = new BasicAttributeDefinition(UUID.fromString("5c94d39c-f10e-11e3-ae7d-7719884a01c5"), "dateTime", DateTimeUnit.INSTANCE, false);
	
	public static UUID INSTANCE_UUID = UUID.fromString("541ce254-f10e-11e3-bac5-a75fc2793419");

	public static ClockTickEventDefinition INSTANCE = new ClockTickEventDefinition();
	
	public ClockTickEventDefinition() {
		super(INSTANCE_UUID, 
				"Time Changed", 
				DATETIME_ATTRIBUTE_DEF);
	}
	
}
