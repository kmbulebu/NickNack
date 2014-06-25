package com.oakcity.nicknack.providers.xbmc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import com.oakcity.nicknack.core.actions.Action.ActionDefinition;
import com.oakcity.nicknack.core.events.Event;
import com.oakcity.nicknack.core.events.Event.EventDefinition;
import com.oakcity.nicknack.core.providers.OnEventListener;
import com.oakcity.nicknack.core.providers.Provider;
import com.oakcity.nicknack.core.units.Unit;
import com.oakcity.nicknack.providers.xbmc.events.PauseEventDefinition;
import com.oakcity.nicknack.providers.xbmc.events.PlayEventDefinition;
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
	private XbmcClient xbmcClient;
	
	public XbmcProvider() {
		eventDefinitions = new ArrayList<EventDefinition>(3);
		eventDefinitions.add(PlayEventDefinition.INSTANCE);
		eventDefinitions.add(PauseEventDefinition.INSTANCE);
		eventDefinitions.add(StopEventDefinition.INSTANCE);
		
		actionDefinitions = new ArrayList<ActionDefinition>(0);
		
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
	public List<Unit> getUnits() {
		// TODO Auto-generated method stub
		return null;
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
	public void init(OnEventListener onEventListener) throws Exception {
		xbmcClient = new XbmcClient();
		xbmcClient.setListener(this);
		xbmcClient.connect("192.168.69.2", 9090);
		
		this.onEventListener = onEventListener;
	}

	@Override
	public void onMessageReceived(JsonRpc message) {
		if (onEventListener != null) {
			final Event event = messageMapper.map(message);
			
			if (event != null) {
				onEventListener.onEvent(event);
			}
		}
	}

}
