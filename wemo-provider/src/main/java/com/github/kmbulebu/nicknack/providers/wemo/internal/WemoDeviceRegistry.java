package com.github.kmbulebu.nicknack.providers.wemo.internal;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.cybergarage.upnp.ControlPoint;
import org.cybergarage.upnp.Device;
import org.cybergarage.upnp.Service;
import org.cybergarage.upnp.device.DeviceChangeListener;

import com.github.kmbulebu.nicknack.providers.wemo.WemoProvider;

public class WemoDeviceRegistry implements DeviceChangeListener {
	
	private static final Logger LOG = LogManager.getLogger(WemoProvider.LOGGER_NAME);
	
	public static final String DEVICE_URN_SWITCH = "urn:Belkin:device:controllee:1";
	
	private final Map<String, WemoDevice> urnToDeviceMap = new ConcurrentHashMap<>();
	private final Map<String, WemoDevice> sidToDeviceMap = new ConcurrentHashMap<>();
	
	private final ControlPoint controlPoint;
	
	public WemoDeviceRegistry(ControlPoint controlPoint) {
		this.controlPoint = controlPoint;
	}

	@Override
	public void deviceAdded(Device dev) {
		if (isWemoSwitch(dev)) {
			final WemoSwitchOutlet wemoSwitch = new WemoSwitchOutlet(dev);
			urnToDeviceMap.put(dev.getUDN(), wemoSwitch);
			if (LOG.isInfoEnabled()) {
				LOG.info("Found new Wemo Device: " + dev.getFriendlyName() + " (" + dev.getUDN() + ')');
			}
			
			final Service basicEventService = dev.getService("urn:Belkin:service:basicevent:1");
			controlPoint.subscribe(basicEventService);
			final String sid = basicEventService.getSID();
			sidToDeviceMap.put(sid, wemoSwitch);
		}
	}

	@Override
	public void deviceRemoved(Device dev) {
		if (isWemoSwitch(dev)) {
			urnToDeviceMap.remove(dev.getUDN());
			final Service basicEventService = dev.getService("urn:Belkin:service:basicevent:1");
			if (basicEventService != null && basicEventService.getSID() != null) {
				sidToDeviceMap.remove(basicEventService.getSID());
			}
			if (LOG.isInfoEnabled()) {
				LOG.info("Removed Wemo Device: " + dev.getFriendlyName() + " (" + dev.getUDN() + ')');
			}
		}
	}
	
	public WemoDevice findByBasicEventServiceSID(String uuid) {
		return sidToDeviceMap.get(uuid);
	}
	
	public List<WemoDevice> findByFriendlyName(String friendlyName) {
		final List<WemoDevice> found = new LinkedList<>();
		
		for (WemoDevice device : urnToDeviceMap.values()) {
			if (friendlyName.equals(device.getFriendlyName())) {
				found.add(device);
			}
		}
		
		if (LOG.isDebugEnabled()) {
			LOG.debug("Found " + found.size() + " devices matching '" + friendlyName + "'.");
		}
		
		return found;
		
	}
	
	public Collection<WemoDevice> getAll() {
		return Collections.unmodifiableCollection(urnToDeviceMap.values());
	}
	
	protected static boolean isWemoSwitch(Device dev) {
		return dev != null && dev.getDeviceType() != null && DEVICE_URN_SWITCH.equals(dev.getDeviceType());
	}
	
	
	
	

}
