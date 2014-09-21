package com.oakcity.nicknack.providers.xbmc;

import java.net.URI;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
	
	private static final Logger logger = LogManager.getLogger(XbmcProvider.LOGGER_NAME);
	
	public Event map(URI uri, JsonRpc message) {
		if (logger.isTraceEnabled()) {
			logger.entry(uri, message);
		}
		Event event = null;
		if (message != null && message.getMethod() != null) {
			switch (message.getMethod()) {
				case "Player.OnPlay":
					event = mapToPlayerEvent(uri, message, PlayEventDefinition.INSTANCE);
					break;
				case "Player.OnStop":
					event = mapToPlayerEvent(uri, message, StopEventDefinition.INSTANCE);
					break;
				case "Player.OnPause":
					event = mapToPlayerEvent(uri, message, PauseEventDefinition.INSTANCE);
					break;
			}
		}
		if (logger.isTraceEnabled()) {
			logger.exit(event);
		}
		return event;
	}
	
	protected Event mapToPlayerEvent(URI uri, JsonRpc message, final EventDefinition eventDefinition) {
		if (logger.isTraceEnabled()) {
			logger.entry(uri, message, eventDefinition);
		}
		if (uri == null) {
			logger.error("URI is null");
			if (logger.isTraceEnabled()) {
				logger.exit(null);
			}
			return null;
		}
		
		final BasicTimestampedEvent event = new BasicTimestampedEvent(eventDefinition);
		
		// Add the sender's host to the attributes
		event.setAttribute(SourceHostAttributeDefinition.INSTANCE, uri.getHost());
			
		
		// Verify params is not null.
		if (message.getParams() == null) {
			logger.error("Params is null");
			if (logger.isTraceEnabled()) {
				logger.exit(null);
			}
			return null;
		}
		
		final JsonNode data = message.getParams().get("data");
		if (data == null) {
			logger.error("Data is null");
			if (logger.isTraceEnabled()) {
				logger.exit(null);
			}
			return null;
		}
		
		final JsonNode item = data.get("item");
		if (item == null) {
			logger.error("Item is null");
			if (logger.isTraceEnabled()) {
				logger.exit(null);
			}
		}
		
		final JsonNode titleNode = item.get("title");
		if (titleNode != null) {
			event.setAttribute(PlayerItemTitleAttributeDefinition.INSTANCE, titleNode.asText());
		}
		
		final JsonNode typeNode = item.get("type");
		if (typeNode == null) {
			logger.error("Type is null");
			if (logger.isTraceEnabled()) {
				logger.exit(null);
			}
		}
		event.setAttribute(PlayerItemTypeAttributeDefinition.INSTANCE, typeNode.asText());
		
		if (logger.isTraceEnabled()) {
			logger.exit(event);
		}
		return event;
	}

}
