package com.github.kmbulebu.nicknack.providers.wemo.internal;

import org.cybergarage.upnp.Action;
import org.cybergarage.upnp.Device;

public class WemoSwitchOutlet implements WemoDevice {
	
	private final Device device;
	
	public WemoSwitchOutlet(Device device) {
		this.device = device;
	}

	@Override
	public String getFriendlyName() {
		return device.getFriendlyName();
	}

	@Override
	public String getUniqueDeviceName() {
		return device.getUDN();
	}

	@Override
	public void switchOnOrOff(boolean isOn) throws WemoException {
		 final Action upnpAction = device.getAction("SetBinaryState");
		 upnpAction.setArgumentValue("BinaryState", isOn ? 1 : 0);
		 if (upnpAction.postControlAction()) {
			 throw new WemoException("Failed to set BinaryState.");
		 }
	}

	@Override
	public boolean isOn() throws WemoException {
		final Action action = device.getAction("GetBinaryState");
    	if (!action.postControlAction() || action.getArgument("BinaryState") == null) {
    		throw new WemoException("Failed to get BinaryState.");
    	}
    	final int binaryState = action.getArgument("BinaryState").getIntegerValue();
    	if (binaryState == 0) {
    		return false;
    	} else if (binaryState == 1) {
    		return true;
    	} else {
    		throw new WemoException("Unrecognized BinaryState value of " + binaryState);
    	}
	}
	
	
	
	

}
