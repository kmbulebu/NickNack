package com.github.kmbulebu.nicknack.providers.xbmc;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.configuration.Configuration;
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
import com.github.kmbulebu.nicknack.core.states.State;
import com.github.kmbulebu.nicknack.core.states.StateDefinition;
import com.github.kmbulebu.nicknack.providers.xbmc.actions.ShowNotificationActionDefinition;
import com.github.kmbulebu.nicknack.providers.xbmc.actions.parameters.HostParameterDefinition;
import com.github.kmbulebu.nicknack.providers.xbmc.events.PauseEventDefinition;
import com.github.kmbulebu.nicknack.providers.xbmc.events.PlayEventDefinition;
import com.github.kmbulebu.nicknack.providers.xbmc.events.PlayerItemTypeAttributeDefinition;
import com.github.kmbulebu.nicknack.providers.xbmc.events.SourceHostAttributeDefinition;
import com.github.kmbulebu.nicknack.providers.xbmc.events.StopEventDefinition;
import com.github.kmbulebu.nicknack.providers.xbmc.json.JsonRpc;

/**
 * Provides real time clock capabilities to Nick Nack.
 * @author kmbulebu
 *
 */
public class XbmcProvider implements Provider, XbmcClient.OnMessageReceivedListener {
	
	public static final UUID PROVIDER_UUID = UUID.fromString("12c97e3a-707d-472b-ad84-3f95897a9787");
	
	public static final String LOGGER_NAME = "xbmc-provider";
	
	private static final Logger logger = LogManager.getLogger(LOGGER_NAME);
	
	private final List<EventDefinition> eventDefinitions;
	private final List<ActionDefinition> actionDefinitions;
	private final XbmcMessageMapper messageMapper;
	private OnEventListener onEventListener = null;
	private List<XbmcClient> xbmcClients;
	
	private Configuration configuration;
	
	public XbmcProvider() {
		if (logger.isTraceEnabled()) {
			logger.entry();
		}
		eventDefinitions = new ArrayList<EventDefinition>(3);
		eventDefinitions.add(PlayEventDefinition.INSTANCE);
		eventDefinitions.add(PauseEventDefinition.INSTANCE);
		eventDefinitions.add(StopEventDefinition.INSTANCE);
		
		actionDefinitions = new ArrayList<ActionDefinition>(1);
		actionDefinitions.add(ShowNotificationActionDefinition.INSTANCE);
		
		messageMapper = new XbmcMessageMapper();
		if (logger.isTraceEnabled()) {
			logger.exit();
		}
	}
	
	@Override
	public UUID getUuid() {
		return PROVIDER_UUID;
	}
	@Override
	public String getName() {
		return "Kodi (formerly XBMC)";
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
	public void init(Configuration configuration, OnEventListener onEventListener) throws Exception {
		if (logger.isTraceEnabled()) {
			logger.entry(configuration, onEventListener);
		}
		xbmcClients = new ArrayList<>();
		this.configuration = configuration;
		
		int i = 0;
		// TODO Switch to string array
		while (configuration.containsKey("host" + i)) {
			String[] split = configuration.getString("host" + i).split(":");
			if (split.length == 2 && split[1].matches("\\d+")) {
				if (logger.isDebugEnabled()) {
					logger.debug("Creating XBMC client for " + split[0] + " " + split[1]);
				}
				XbmcClient client = new XbmcClient();
				client.setListener(this);
				client.connect(split[0], Integer.parseInt(split[1]));
				xbmcClients.add(client);
			}
			i++;
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
	public Map<String, String> getAttributeDefinitionValues(UUID eventDefinitionUuid, UUID attributeDefinitionUuid) {
		if (logger.isTraceEnabled()) {
			logger.entry(eventDefinitionUuid, attributeDefinitionUuid);
		}
		
		final Map<String, String> result;
		if (PlayerItemTypeAttributeDefinition.INSTANCE.getUUID().equals(attributeDefinitionUuid)) {
			result = PlayerItemTypeAttributeDefinition.VALUES;
		} else if (SourceHostAttributeDefinition.INSTANCE.getUUID().equals(attributeDefinitionUuid)) {
			result = buildSourceHostValues();
		} else {
			result = null;
		}

		if (logger.isTraceEnabled()) {
			logger.exit(result);
		}
		return result;
	}
	
	private Map<String, String> buildSourceHostValues() {
		if (logger.isTraceEnabled()) {
			logger.entry();
		}
		final Map<String, String> values = new HashMap<>();
		int i = 0;
		while (configuration.containsKey("host" + i)) {
			String[] split = configuration.getString("host" + i).split(":");
			if (split.length == 2 && split[1].matches("\\d+")) {
				values.put(split[0], split[0]);
			}
			i++;
		}
		if (logger.isTraceEnabled()) {
			logger.exit(values);
		}
		return values;
	}

	@Override
	public void run(Action action) throws ActionFailureException, ActionParameterException {
		if (logger.isTraceEnabled()) {
			logger.entry(action);
		}
		if (ShowNotificationActionDefinition.INSTANCE.getUUID().equals(action.getAppliesToActionDefinition())) {
			final String host = action.getParameters().get(HostParameterDefinition.DEF_UUID);
			if (host == null) {
				final ActionParameterException t = new ActionParameterException(HostParameterDefinition.INSTANCE.getName() + " is missing.");
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
