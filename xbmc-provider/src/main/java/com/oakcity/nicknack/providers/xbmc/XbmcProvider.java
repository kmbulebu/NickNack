package com.oakcity.nicknack.providers.xbmc;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.configuration.Configuration;

import com.oakcity.nicknack.core.actions.Action;
import com.oakcity.nicknack.core.actions.ActionDefinition;
import com.oakcity.nicknack.core.actions.ActionFailureException;
import com.oakcity.nicknack.core.actions.ActionParameterException;
import com.oakcity.nicknack.core.events.Event;
import com.oakcity.nicknack.core.events.EventDefinition;
import com.oakcity.nicknack.core.providers.OnEventListener;
import com.oakcity.nicknack.core.providers.Provider;
import com.oakcity.nicknack.providers.xbmc.actions.ShowNotificationActionDefinition;
import com.oakcity.nicknack.providers.xbmc.actions.parameters.HostParameterDefinition;
import com.oakcity.nicknack.providers.xbmc.events.PauseEventDefinition;
import com.oakcity.nicknack.providers.xbmc.events.PlayEventDefinition;
import com.oakcity.nicknack.providers.xbmc.events.PlayerItemTypeAttributeDefinition;
import com.oakcity.nicknack.providers.xbmc.events.SourceHostAttributeDefinition;
import com.oakcity.nicknack.providers.xbmc.events.StopEventDefinition;
import com.oakcity.nicknack.providers.xbmc.json.JsonRpc;

/**
 * Provides real time clock capabilities to Nick Nack.
 * @author kmbulebu
 *
 */
public class XbmcProvider implements Provider, XbmcClient.OnMessageReceivedListener {
	
	public static final UUID PROVIDER_UUID = UUID.fromString("12c97e3a-707d-472b-ad84-3f95897a9787");
	
	private final List<EventDefinition> eventDefinitions;
	private final List<ActionDefinition> actionDefinitions;
	private final XbmcMessageMapper messageMapper;
	private OnEventListener onEventListener = null;
	private List<XbmcClient> xbmcClients;
	
	private Configuration configuration;
	
	public XbmcProvider() {
		eventDefinitions = new ArrayList<EventDefinition>(3);
		eventDefinitions.add(PlayEventDefinition.INSTANCE);
		eventDefinitions.add(PauseEventDefinition.INSTANCE);
		eventDefinitions.add(StopEventDefinition.INSTANCE);
		
		actionDefinitions = new ArrayList<ActionDefinition>(1);
		actionDefinitions.add(ShowNotificationActionDefinition.INSTANCE);
		
		messageMapper = new XbmcMessageMapper();
	
	}
	
	@Override
	public UUID getUuid() {
		return PROVIDER_UUID;
	}
	@Override
	public String getName() {
		return "XBMC";
	}
	@Override
	public String getAuthor() {
		return "Nick Nack";
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
	public List<ActionDefinition> getActionDefinitions() {
		return Collections.unmodifiableList(actionDefinitions);
	}
	
	@Override
	public void init(Configuration configuration, OnEventListener onEventListener) throws Exception {
		xbmcClients = new ArrayList<>();
		this.configuration = configuration;
		
		int i = 0;
		// TODO Switch to string array
		while (configuration.containsKey("host" + i)) {
			String[] split = configuration.getString("host" + i).split(":");
			if (split.length == 2 && split[1].matches("\\d+")) {
				XbmcClient client = new XbmcClient();
				client.setListener(this);
				client.connect(split[0], Integer.parseInt(split[1]));
				xbmcClients.add(client);
			}
			i++;
		}
		
		this.onEventListener = onEventListener;
	}

	@Override
	public void onMessageReceived(URI uri, JsonRpc message) {
		if (onEventListener != null) {
			final Event event = messageMapper.map(uri, message);
					
			if (event != null) {
				onEventListener.onEvent(event);
			}
		}
	}

	@Override
	public Map<String, String> getAttributeDefinitionValues(UUID eventDefinitionUuid, UUID attributeDefinitionUuid) {
		if (PlayerItemTypeAttributeDefinition.INSTANCE.getUUID().equals(attributeDefinitionUuid)) {
			return PlayerItemTypeAttributeDefinition.VALUES;
		} else if (SourceHostAttributeDefinition.INSTANCE.getUUID().equals(attributeDefinitionUuid)) {
			return buildSourceHostValues();
		} else {
			return null;
		}

	}
	
	private Map<String, String> buildSourceHostValues() {
		final Map<String, String> values = new HashMap<>();
		int i = 0;
		while (configuration.containsKey("host" + i)) {
			String[] split = configuration.getString("host" + i).split(":");
			if (split.length == 2 && split[1].matches("\\d+")) {
				values.put(split[0], split[0]);
			}
			i++;
		}
		return values;
	}

	@Override
	public void run(Action action) throws ActionFailureException, ActionParameterException {
		if (ShowNotificationActionDefinition.INSTANCE.getUUID().equals(action.getAppliesToActionDefinition())) {
			final String host = action.getParameters().get(HostParameterDefinition.DEF_UUID);
			if (host == null) {
				throw new ActionParameterException(HostParameterDefinition.INSTANCE.getName() + " is missing.");
			}
			final XbmcClient client = findClient(host);
			if (client == null) {
				throw new ActionParameterException("Host " + host + " could not be found.");
			}
			ShowNotificationActionDefinition.INSTANCE.run(action, client);
		}
		
	}
	
	protected XbmcClient findClient(String host) {
		for (XbmcClient aClient : xbmcClients) {
			if (aClient.getHost().equalsIgnoreCase(host)) {
				return aClient;
			}
		}
		return null;
	}

}
