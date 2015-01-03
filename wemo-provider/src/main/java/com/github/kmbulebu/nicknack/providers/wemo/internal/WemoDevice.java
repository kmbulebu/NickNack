package com.github.kmbulebu.nicknack.providers.wemo.internal;

public interface WemoDevice {
	
	public String getFriendlyName();
	
	public String getUniqueDeviceName();
	
	public void switchOnOrOff(boolean isOn) throws WemoException;
	
	public boolean isOn() throws WemoException;

}
