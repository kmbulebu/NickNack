package com.github.kmbulebu.nicknack.providers.xbmc;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.kmbulebu.nicknack.core.actions.Action;
import com.github.kmbulebu.nicknack.core.actions.ActionDefinition;
import com.github.kmbulebu.nicknack.core.actions.ActionFailureException;
import com.github.kmbulebu.nicknack.core.actions.ActionParameterException;
import com.github.kmbulebu.nicknack.core.events.Event;
import com.github.kmbulebu.nicknack.core.events.EventDefinition;
import com.github.kmbulebu.nicknack.core.providers.OnEventListener;
import com.github.kmbulebu.nicknack.core.providers.Provider;
import com.github.kmbulebu.nicknack.core.providers.ProviderConfiguration;
import com.github.kmbulebu.nicknack.core.providers.settings.SettingDefinition;
import com.github.kmbulebu.nicknack.core.states.State;
import com.github.kmbulebu.nicknack.core.states.StateDefinition;
import com.github.kmbulebu.nicknack.providers.xbmc.actions.ShowNotificationActionDefinition;
import com.github.kmbulebu.nicknack.providers.xbmc.attributes.HostAttributeDefinition;
import com.github.kmbulebu.nicknack.providers.xbmc.attributes.PlayerItemTypeAttributeDefinition;
import com.github.kmbulebu.nicknack.providers.xbmc.events.PauseEventDefinition;
import com.github.kmbulebu.nicknack.providers.xbmc.events.PlayEventDefinition;
import com.github.kmbulebu.nicknack.providers.xbmc.events.StopEventDefinition;
import com.github.kmbulebu.nicknack.providers.xbmc.internal.JsonRpc;
import com.github.kmbulebu.nicknack.providers.xbmc.internal.XbmcClient;
import com.github.kmbulebu.nicknack.providers.xbmc.internal.XbmcMessageMapper;
import com.github.kmbulebu.nicknack.providers.xbmc.settings.HostsSettingDefinition;

/**
 * Provides real time clock capabilities to Nick Nack.
 * @author kmbulebu
 *
 */
public class XbmcProvider implements Provider, XbmcClient.OnMessageReceivedListener {
	
	public static final UUID PROVIDER_UUID = UUID.fromString("12c97e3a-707d-472b-ad84-3f95897a9787");
	
	public static final String LOGGER_NAME = "xbmc-provider";
	
	private static final Logger logger = LogManager.getLogger(LOGGER_NAME);
	
	private static final int DEFAULT_PORT = 9090;
	
	private List<EventDefinition> eventDefinitions = null;
	private List<ActionDefinition> actionDefinitions = null;
	private XbmcMessageMapper messageMapper = null;
	private OnEventListener onEventListener = null;
	private List<XbmcClient> xbmcClients = null;
	private Map<String, String> hostNameValues;
	
	private final List<SettingDefinition<?,?>> settingDefinitions;
	
	private final HostsSettingDefinition hostSettingsDefinition;
	
	public XbmcProvider() {
		settingDefinitions = new ArrayList<SettingDefinition<?,?>>(1);
		hostSettingsDefinition = new HostsSettingDefinition();
		settingDefinitions.add(hostSettingsDefinition);
	}
	
	@Override
	public UUID getUuid() {
		return PROVIDER_UUID;
	}
	@Override
	public String getName() {
		return "Kodi";
	}
	@Override
	public String getAuthor() {
		return "NickNack";
	}
	@Override
	public int getVersion() {
		return 1;
	}
	
	@Override
	public List<EventDefinition> getEventDefinitions() {
		return Collections.unmodifiableList(eventDefinitions);
	}
	
	@Override
	public Collection<StateDefinition> getStateDefinitions() {
		return Collections.emptyList();
	}
	
	@Override
	public List<ActionDefinition> getActionDefinitions() {
		return Collections.unmodifiableList(actionDefinitions);
	}
	
	@Override
	public void init(ProviderConfiguration configuration, OnEventListener onEventListener) throws Exception {
		if (logger.isTraceEnabled()) {
			logger.entry(configuration, onEventListener);
		}
		eventDefinitions = new ArrayList<EventDefinition>(3);
		eventDefinitions.add(PlayEventDefinition.INSTANCE);
		eventDefinitions.add(PauseEventDefinition.INSTANCE);
		eventDefinitions.add(StopEventDefinition.INSTANCE);
		
		actionDefinitions = new ArrayList<ActionDefinition>(1);
		actionDefinitions.add(ShowNotificationActionDefinition.INSTANCE);
		
		messageMapper = new XbmcMessageMapper();
		
		xbmcClients = new ArrayList<>();
		
		hostNameValues = new HashMap<>();
		final List<String> hosts = configuration.getValues(hostSettingsDefinition);
				
		for (String host : hosts) {
			XbmcClient client = new XbmcClient();
			client.setListener(this);
			client.connect(host, DEFAULT_PORT);
			xbmcClients.add(client);
			hostNameValues.put(host, host);
		}
		
		if (logger.isInfoEnabled()) {
			logger.info("XBMC Plugin Ready.");
		}
		
		this.onEventListener = onEventListener;
		if (logger.isTraceEnabled()) {
			logger.exit();
		}
	}
	
	@Override
	public void shutdown() throws Exception {
		onEventListener = null;
		for (XbmcClient client : xbmcClients) {
			client.setListener(null);
			client.disconnect();
		}
		xbmcClients = null;
		
		messageMapper = null;
		
		actionDefinitions = null;
		eventDefinitions = null;
		
		hostNameValues = null;
	}
	
	@Override
	public List<? extends SettingDefinition<?,?>> getSettingDefinitions() {
		return Collections.unmodifiableList(settingDefinitions);
	}

	@Override
	public void onMessageReceived(URI uri, JsonRpc message) {
		if (logger.isTraceEnabled()) {
			logger.entry(uri, message);
		}
		if (onEventListener != null) {
			final Event event = messageMapper.map(uri, message);
					
			if (event != null) {
				onEventListener.onEvent(event);
			}
		}
		if (logger.isTraceEnabled()) {
			logger.exit();
		}
	}

	@Override
	public Map<String, String> getAttributeDefinitionValues(UUID attributeDefinitionUuid) {
		if (logger.isTraceEnabled()) {
			logger.entry(attributeDefinitionUuid);
		}
		
		final Map<String, String> result;
		if (PlayerItemTypeAttributeDefinition.INSTANCE.getUUID().equals(attributeDefinitionUuid)) {
			result = PlayerItemTypeAttributeDefinition.VALUES;
		} else if (HostAttributeDefinition.INSTANCE.getUUID().equals(attributeDefinitionUuid)) {
			result = Collections.unmodifiableMap(hostNameValues);
		} else {
			result = null;
		}

		if (logger.isTraceEnabled()) {
			logger.exit(result);
		}
		return result;
	}

	@Override
	public void run(Action action) throws ActionFailureException, ActionParameterException {
		if (logger.isTraceEnabled()) {
			logger.entry(action);
		}
		if (ShowNotificationActionDefinition.INSTANCE.getUUID().equals(action.getAppliesToActionDefinition())) {
			final String host = action.getAttributes().get(HostAttributeDefinition.DEF_UUID);
			if (host == null) {
				final ActionParameterException t = new ActionParameterException(HostAttributeDefinition.INSTANCE.getName() + " is missing.");
				if (logger.isTraceEnabled()) {
					logger.throwing(t);
				}
				throw t;
			}
			if (logger.isInfoEnabled()) {
				logger.info("Showing notification on host: " + host);
			}
			if ("all".equalsIgnoreCase(host)) {
				for (XbmcClient client : xbmcClients) {
					ShowNotificationActionDefinition.INSTANCE.run(action, client);
				}
			} else {
				final XbmcClient client = findClient(host);
				if (client == null) {
					final ActionParameterException t = new ActionParameterException("Host " + host + " could not be found.");
					if (logger.isTraceEnabled()) {
						logger.throwing(t);
					}
					throw t;
				}
				ShowNotificationActionDefinition.INSTANCE.run(action, client);
			}
		}
		if (logger.isTraceEnabled()) {
			logger.exit();
		}
	}
	
	protected XbmcClient findClient(String host) {
		if (logger.isTraceEnabled()) {
			logger.entry(host);
		}
		XbmcClient theClient = null;
		for (XbmcClient aClient : xbmcClients) {
			if (aClient.getHost().equalsIgnoreCase(host)) {
				theClient = aClient;
				break;
			}
		}
		
		if (logger.isTraceEnabled()) {
			logger.exit(theClient);
		}
		return theClient;
	}
	
	@Override
	public List<State> getStates(UUID stateDefinitionUuid) {
		return Collections.emptyList();
	}

}
