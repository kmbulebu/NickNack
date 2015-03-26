package com.github.kmbulebu.nicknack.basicproviders.wol;

import java.io.IOException;
import java.util.UUID;

import com.github.kmbulebu.nicknack.core.actions.Action;
import com.github.kmbulebu.nicknack.core.actions.ActionAttributeException;
import com.github.kmbulebu.nicknack.core.actions.ActionFailureException;
import com.github.kmbulebu.nicknack.core.actions.BasicActionDefinition;
import com.github.kmbulebu.nicknack.core.attributes.impl.MacAddressAttributeDefinition;
import com.github.kmbulebu.nicknack.core.providers.Provider;

public class WakeOnLanActionDefinition extends BasicActionDefinition {
	
	public static final UUID WOL_ACTION_UUID = UUID.fromString("e3e88ad5-4f62-441f-adbc-d76ff953a624");
	public static final WakeOnLanActionDefinition INSTANCE = new WakeOnLanActionDefinition();
	
	public WakeOnLanActionDefinition() {
		super(WOL_ACTION_UUID, 
				"Wake Up Network Device", 
				"Wake up a device on your local network using Wake-up On Lan (WOL).",
				DeviceMacAddressAttributeDefinition.INSTANCE);
	}
	
	public static class DeviceMacAddressAttributeDefinition extends MacAddressAttributeDefinition {

		public static final UUID MAC_ADDRESS_PARAMETER_UUID = UUID.fromString("e178a7a1-c0c8-4233-98fb-4be49978b501");
		public static final DeviceMacAddressAttributeDefinition INSTANCE = new DeviceMacAddressAttributeDefinition();
		
		public DeviceMacAddressAttributeDefinition() {
			super(MAC_ADDRESS_PARAMETER_UUID, "Device Mac Address", true);
		}
		
	}

	@Override
	public void run(Action action, Provider provider) throws ActionFailureException, ActionAttributeException {
		final Object macAddress = action.getAttributes().get(DeviceMacAddressAttributeDefinition.MAC_ADDRESS_PARAMETER_UUID);
		if (macAddress == null) {
			throw new ActionAttributeException(DeviceMacAddressAttributeDefinition.INSTANCE.getName() + " is required.");
		}
		
		final WakeOnLan wol = new WakeOnLan((String) macAddress);
		
		try {
			wol.send();
		} catch (IOException e) {
			throw new ActionFailureException(e);
		}
	}

}
