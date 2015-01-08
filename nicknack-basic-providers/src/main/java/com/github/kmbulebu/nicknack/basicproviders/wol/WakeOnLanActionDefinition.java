package com.github.kmbulebu.nicknack.basicproviders.wol;

import java.util.UUID;

import com.github.kmbulebu.nicknack.core.actions.BasicActionDefinition;
import com.github.kmbulebu.nicknack.core.attributes.impl.MacAddressAttributeDefinition;

public class WakeOnLanActionDefinition extends BasicActionDefinition {
	
	public static final UUID WOL_ACTION_UUID = UUID.fromString("e3e88ad5-4f62-441f-adbc-d76ff953a624");
	public static final WakeOnLanActionDefinition INSTANCE = new WakeOnLanActionDefinition();
	
	public WakeOnLanActionDefinition() {
		super(WOL_ACTION_UUID, 
				"Wake Up Network Device", 
				"Wake up a device on your local network using Wake-up On Lan (WOL).",
				DeviceMacAddressParameterDefinition.INSTANCE);
	}
	
	public static class DeviceMacAddressParameterDefinition extends MacAddressAttributeDefinition {

		public static final UUID MAC_ADDRESS_PARAMETER_UUID = UUID.fromString("e178a7a1-c0c8-4233-98fb-4be49978b501");
		public static final DeviceMacAddressParameterDefinition INSTANCE = new DeviceMacAddressParameterDefinition();
		
		public DeviceMacAddressParameterDefinition() {
			super(MAC_ADDRESS_PARAMETER_UUID, "Device Mac Address", true);
		}
		
	}

}
