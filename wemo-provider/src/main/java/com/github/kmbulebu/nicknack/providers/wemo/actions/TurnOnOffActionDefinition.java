package com.github.kmbulebu.nicknack.providers.wemo.actions;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.kmbulebu.nicknack.core.actions.Action;
import com.github.kmbulebu.nicknack.core.actions.ActionFailureException;
import com.github.kmbulebu.nicknack.core.actions.ActionParameterException;
import com.github.kmbulebu.nicknack.providers.wemo.WemoProvider;
import com.github.kmbulebu.nicknack.providers.wemo.internal.WemoDevice;
import com.github.kmbulebu.nicknack.providers.wemo.internal.WemoDeviceRegistry;
import com.github.kmbulebu.nicknack.providers.wemo.internal.WemoException;

public class TurnOnOffActionDefinition extends WemoActionDefinition {
	
	private static final Logger LOG = LogManager.getLogger(WemoProvider.LOGGER_NAME);

	public static final UUID DEF_UUID = UUID.fromString("203dd21e-b0a4-42a9-8598-71936ea119fd");
	
	private final WemoDeviceRegistry deviceRegistry;

	public TurnOnOffActionDefinition(WemoDeviceRegistry deviceRegistry) {
		super(DEF_UUID, "Turn On or Off",
				OnParameterDefinition.INSTANCE);
		this.deviceRegistry = deviceRegistry;
	}

	@Override
	public void run(Action action) throws ActionFailureException, ActionParameterException {
		final String friendlyName = action.getParameters().get(FriendlyNameParameterDefinition.INSTANCE.getUUID());
		final String onStr = action.getParameters().get(OnParameterDefinition.INSTANCE.getUUID());
		
		if (friendlyName == null || friendlyName.length() < 1) {
			throw new ActionParameterException(FriendlyNameParameterDefinition.INSTANCE.getName() + " parameter is required.");
		}
		
		if (onStr == null || onStr.length() < 1) {
			throw new ActionParameterException(OnParameterDefinition.INSTANCE.getName() + " parameter is required.");
		}
		
		boolean newSwitchOnValue;
		if ("on".equalsIgnoreCase(onStr) || "true".equalsIgnoreCase(onStr)) {
			newSwitchOnValue = true;
		} else if ("off".equalsIgnoreCase(onStr) || "false".equalsIgnoreCase(onStr)) {
			newSwitchOnValue = false;
		} else {
			throw new ActionParameterException(OnParameterDefinition.INSTANCE.getName() + " parameter value is not recognized."); 
		}
		
		final List<WemoDevice> failedToChange = new LinkedList<>();
		for (WemoDevice device : deviceRegistry.findByFriendlyName(friendlyName)) {
			if (LOG.isInfoEnabled()) {
				LOG.info("Setting Wemo Device switch, " + device.getFriendlyName() + " (" + device.getUniqueDeviceName() + ") to " + newSwitchOnValue + ".");
			}
			try {
				device.switchOnOrOff(newSwitchOnValue);
			} catch (WemoException e) {
				if (LOG.isWarnEnabled()) {
					LOG.warn("Failedd to change " + device.getFriendlyName() + " (" + device.getUniqueDeviceName() + ") to " + newSwitchOnValue + ".", e);
				}
				failedToChange.add(device);
			}
		}
		
		if (!failedToChange.isEmpty()) {
			final String[] failNames = new String[failedToChange.size()];
			for (int i = 0; i < failedToChange.size(); i++) {
				final WemoDevice device = failedToChange.get(i);
				failNames[i] = device.getFriendlyName() + " (" + device.getUniqueDeviceName() + ")";
			}
			throw new ActionFailureException("Could not switch all matching Wemo devices (" + Arrays.toString(failNames) + ')');
		}
		
	}
	
}
