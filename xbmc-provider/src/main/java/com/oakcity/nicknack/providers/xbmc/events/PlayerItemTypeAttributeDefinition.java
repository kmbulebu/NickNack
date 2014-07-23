package com.oakcity.nicknack.providers.xbmc.events;

import java.util.UUID;

import com.oakcity.nicknack.core.events.BasicAttributeDefinition;
import com.oakcity.nicknack.core.units.StringUnit;

public class PlayerItemTypeAttributeDefinition extends BasicAttributeDefinition {
	
	public static final PlayerItemTypeAttributeDefinition INSTANCE = new PlayerItemTypeAttributeDefinition();

	public PlayerItemTypeAttributeDefinition() {
		super(UUID.fromString("01309847-cd0f-4170-b0cc-3010dbf1f566"), "Media Type", StringUnit.INSTANCE, false);
	}
	
	/*private static final Map<String, String> buildChoices() {
		final Map<String, String> choices = new HashMap<>();
		choices.put("Unknown", "unknown");
		choices.put("Movie", "movie");
		choices.put("Episode", "episode");
		choices.put("Music Video", "musicvideo");
		choices.put("Song", "song");
		choices.put("Picture", "picture");
		choices.put("TV Channel", "channel");
		return choices;
	};*/

}
