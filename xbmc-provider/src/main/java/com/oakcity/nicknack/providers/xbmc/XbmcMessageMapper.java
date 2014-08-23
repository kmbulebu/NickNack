package com.oakcity.nicknack.providers.xbmc;

import java.net.URI;

import com.fasterxml.jackson.databind.JsonNode;
import com.oakcity.nicknack.core.events.Event;
import com.oakcity.nicknack.core.events.EventDefinition;
import com.oakcity.nicknack.core.events.impl.BasicTimestampedEvent;
import com.oakcity.nicknack.providers.xbmc.events.PauseEventDefinition;
import com.oakcity.nicknack.providers.xbmc.events.PlayEventDefinition;
import com.oakcity.nicknack.providers.xbmc.events.PlayerItemTitleAttributeDefinition;
import com.oakcity.nicknack.providers.xbmc.events.PlayerItemTypeAttributeDefinition;
import com.oakcity.nicknack.providers.xbmc.events.SourceHostAttributeDefinition;
import com.oakcity.nicknack.providers.xbmc.events.StopEventDefinition;
import com.oakcity.nicknack.providers.xbmc.json.JsonRpc;

public class XbmcMessageMapper {
	
	public Event map(URI uri, JsonRpc message) {
		if (message != null && message.getMethod() != null) {
			switch (message.getMethod()) {
				case "Player.OnPlay":
					return mapToPlayerEvent(uri, message, PlayEventDefinition.INSTANCE);
				case "Player.OnStop":
					return mapToPlayerEvent(uri, message, StopEventDefinition.INSTANCE);
				case "Player.OnPause":
					return mapToPlayerEvent(uri, message, PauseEventDefinition.INSTANCE);
			}
		}
		return null;
	}
	
	protected Event mapToPlayerEvent(URI uri, JsonRpc message, final EventDefinition eventDefinition) {
		if (uri == null) {
			System.out.println("URI null");
			return null;
		}
		
		final BasicTimestampedEvent event = new BasicTimestampedEvent(eventDefinition);
		
		// Add the sender's host to the attributes
		event.setAttribute(SourceHostAttributeDefinition.INSTANCE, uri.getHost());
			
		
		// Verify params is not null.
		if (message.getParams() == null) {
			System.out.println("Params null");
			return null;
		}
		
		final JsonNode data = message.getParams().get("data");
		if (data == null) {
			System.out.println("Data null");
			return null;
		}
		
		final JsonNode item = data.get("item");
		if (item == null) {
			System.out.println("Item null");
			return null;
		}
		
		final JsonNode titleNode = item.get("title");
		if (titleNode != null) {
			event.setAttribute(PlayerItemTitleAttributeDefinition.INSTANCE, titleNode.asText());
		}
		
		final JsonNode typeNode = item.get("type");
		if (typeNode == null) {
			System.out.println("Type null");
			return null;
		}
		event.setAttribute(PlayerItemTypeAttributeDefinition.INSTANCE, typeNode.asText());
		
		return event;
	}

}
