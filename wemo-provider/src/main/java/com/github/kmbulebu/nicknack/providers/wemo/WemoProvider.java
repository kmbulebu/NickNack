package com.github.kmbulebu.nicknack.providers.wemo;


import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.configuration.Configuration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.cybergarage.upnp.ControlPoint;
import org.cybergarage.upnp.event.EventListener;

import com.github.kmbulebu.nicknack.core.actions.Action;
import com.github.kmbulebu.nicknack.core.actions.ActionDefinition;
import com.github.kmbulebu.nicknack.core.actions.ActionFailureException;
import com.github.kmbulebu.nicknack.core.actions.ActionParameterException;
import com.github.kmbulebu.nicknack.core.events.Event;
import com.github.kmbulebu.nicknack.core.events.EventDefinition;
import com.github.kmbulebu.nicknack.core.providers.OnEventListener;
import com.github.kmbulebu.nicknack.core.providers.Provider;
import com.github.kmbulebu.nicknack.core.states.State;
import com.github.kmbulebu.nicknack.core.states.StateDefinition;
import com.github.kmbulebu.nicknack.providers.wemo.actions.TurnOnOffActionDefinition;
import com.github.kmbulebu.nicknack.providers.wemo.actions.WemoActionDefinition;
import com.github.kmbulebu.nicknack.providers.wemo.events.WemoSwitchOnOffEvent;
import com.github.kmbulebu.nicknack.providers.wemo.events.WemoSwitchOnOffEventDefinition;
import com.github.kmbulebu.nicknack.providers.wemo.internal.WemoDevice;
import com.github.kmbulebu.nicknack.providers.wemo.internal.WemoDeviceRegistry;
import com.github.kmbulebu.nicknack.providers.wemo.internal.WemoException;
import com.github.kmbulebu.nicknack.providers.wemo.states.WemoSwitchState;
import com.github.kmbulebu.nicknack.providers.wemo.states.WemoSwitchStateDefinition;

public class WemoProvider implements Provider, EventListener {
	
	public static final String LOGGER_NAME = "wemo-provider";
	
	private static final Logger LOG = LogManager.getLogger(LOGGER_NAME);
	
	public static final UUID PROVIDER_UUID = UUID.fromString("b1689e42-e0cd-4acc-bcb4-c4a780c86d35");
	
	private final Map<UUID, WemoActionDefinition> actionDefinitions = new HashMap<>();
	private final List<StateDefinition> stateDefinitions = new ArrayList<>();
	private final List<EventDefinition> eventDefinitions = new ArrayList<>();
	
	private ControlPoint controlPoint;
	private WemoDeviceRegistry deviceRegistry;
	
	private ScheduledExecutorService executorService;
	
	private OnEventListener onEventListener;
	
	@Override
	public UUID getUuid() {
		return PROVIDER_UUID;
	}

	@Override
	public String getName() {
		return "Wemo";
	}

	@Override
	public String getAuthor() {
		return "NickNack";
	}

	@Override
	public int getVersion() {
		return 0;
	}

	@Override	
	public Collection<EventDefinition> getEventDefinitions() {
		return Collections.emptyList();
	}
	
	@Override
	public Collection<StateDefinition> getStateDefinitions() {
		return Collections.unmodifiableList(stateDefinitions);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Collection<ActionDefinition> getActionDefinitions() {
		return (Collection) actionDefinitions.values();
	}

	@Override
	public Map<String, String> getAttributeDefinitionValues(UUID eventDefinitionUuid, UUID attributeDefinitionUuid) {
		return null;
	}

	@Override
	public void run(Action action) throws ActionFailureException, ActionParameterException {
		// Look it up.
		final WemoActionDefinition actionDef = actionDefinitions.get(action.getAppliesToActionDefinition());
		if (actionDef == null) {
			throw new ActionFailureException("Action is not provided by the " + getName() + " provider. Please open a bug.");
		}
		
		actionDef.run(action);
	}

	@Override
	public void init(Configuration configuration, OnEventListener onEventListener) throws Exception {
		controlPoint = new ControlPoint();
		
		if (!controlPoint.start()) {
			throw new IOException("Could not start UPNP ControlPoint.");
		}
		
		deviceRegistry = new WemoDeviceRegistry(controlPoint);
		controlPoint.addDeviceChangeListener(deviceRegistry);
		
		// Schedule a new UPNP search for every minute.
		executorService = Executors.newSingleThreadScheduledExecutor();
		executorService.scheduleAtFixedRate(new UpnpSearchRunnable(controlPoint), 0l, 1l, TimeUnit.MINUTES);
		
		if (LOG.isInfoEnabled()) {
			LOG.info("Scheduled a search for Wemo devices every minute.");
		}
		
		stateDefinitions.add(new WemoSwitchStateDefinition());
		eventDefinitions.add(new WemoSwitchOnOffEventDefinition());
		actionDefinitions.put(TurnOnOffActionDefinition.DEF_UUID, new TurnOnOffActionDefinition(deviceRegistry));

		this.onEventListener = onEventListener;
		controlPoint.addEventListener(this);
	}
	
	@Override
	public List<? extends State> getStates(UUID stateDefinitionUuid) {
		if (WemoSwitchStateDefinition.INSTANCE.getUUID().equals(stateDefinitionUuid)) {
			final Collection<WemoDevice> devices = deviceRegistry.getAll();
			final List<WemoSwitchState> states = new ArrayList<>(devices.size());
			
			for (WemoDevice device : devices) {
				try {
					states.add(WemoSwitchState.build(device));
				} catch (WemoException e) {
					if (LOG.isWarnEnabled()) {
						LOG.warn("Could not retrieve Wemo state for device, " + device.getFriendlyName() + ".", e);
					}
				}
			}
			return states;
		} else {
			return Collections.emptyList();
		}
	}
	
	protected void sendEvent(Event event) {
		if (onEventListener != null) {
			onEventListener.onEvent(event);
		}
	}
	
	@Override
	public void eventNotifyReceived(String uuid, long seq, String varName, String value) {
		// Find device by basic event service SID
		final WemoDevice device = deviceRegistry.findByBasicEventServiceSID(uuid);
		
		final WemoSwitchOnOffEvent event = new WemoSwitchOnOffEvent(new Date());
		event.setFriendlyName(device.getFriendlyName());
		if ("BinaryState".equals(varName)) {
			// Create event.
			if ("0".equals(value)) {
				event.setIsOn(false);
				sendEvent(event);
			} else if ("1".equals(value)) {
				event.setIsOn(true);
				sendEvent(event);
			} else {
				if (LOG.isWarnEnabled()) {
					LOG.warn("Unrecognized BinaryState for device " + device.getFriendlyName());
				}
			}
		}
	}
	
	private static class UpnpSearchRunnable implements Runnable {

		private WeakReference<ControlPoint> controlPointRef;
		
		public UpnpSearchRunnable(ControlPoint controlPoint) {
			controlPointRef = new WeakReference<ControlPoint>(controlPoint);
		}

		@Override
		public void run() {
			final ControlPoint cp = controlPointRef.get();
			if (cp == null) {
				if (LOG.isWarnEnabled()) {
					LOG.warn("Skipping search for Wemo devices because UPNP ControlPoint is unavailable.");
				}
			} else {
				if (LOG.isDebugEnabled()) {
					LOG.debug("Initiating search for Wemo devices.");
				}
				cp.search();
			}
		}

		
	}



}
