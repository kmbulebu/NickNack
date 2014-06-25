package com.oakcity.nicknack.basicproviders.wol;

import java.io.IOException;
import java.util.UUID;

import com.oakcity.nicknack.core.actions.Action;
import com.oakcity.nicknack.core.actions.ActionFailureException;
import com.oakcity.nicknack.core.actions.ActionParameterException;
import com.oakcity.nicknack.core.actions.BasicActionDefinition;
import com.oakcity.nicknack.core.actions.parameters.MacAddressParameterDefinition;

public class WakeOnLanActionDefinition extends BasicActionDefinition {
	
	public static final UUID WOL_ACTION_UUID = UUID.fromString("e3e88ad5-4f62-441f-adbc-d76ff953a624");
	public static final WakeOnLanActionDefinition INSTANCE = new WakeOnLanActionDefinition();
	
	public WakeOnLanActionDefinition() {
		super(WOL_ACTION_UUID, WakeOnLanProvider.PROVIDER_UUID, "Wake Up Network Device", DeviceMacAddressParameterDefinition.INSTANCE);
	}

	@Override
	public void run(Action action) throws ActionParameterException, ActionFailureException {
		if (action.getParameters() == null) {
			throw new ActionParameterException("Parameters are required.");
		}
		
		final String macAddress = action.getParameters().get(DeviceMacAddressParameterDefinition.MAC_ADDRESS_PARAMETER_UUID);
		if (macAddress == null) {
			throw new ActionParameterException(DeviceMacAddressParameterDefinition.INSTANCE.getName() + " is required.");
		}
		
		final WakeOnLan wol = new WakeOnLan(macAddress);
		
		try {
			wol.send();
		} catch (IOException e) {
			throw new ActionFailureException(e);
		}
	}
	
	public static class DeviceMacAddressParameterDefinition extends MacAddressParameterDefinition {

		public static final UUID MAC_ADDRESS_PARAMETER_UUID = UUID.fromString("e178a7a1-c0c8-4233-98fb-4be49978b501");
		public static final DeviceMacAddressParameterDefinition INSTANCE = new DeviceMacAddressParameterDefinition();
		
		public DeviceMacAddressParameterDefinition() {
			super(MAC_ADDRESS_PARAMETER_UUID, "Device Mac Address", true);
		}
		
	}

}
