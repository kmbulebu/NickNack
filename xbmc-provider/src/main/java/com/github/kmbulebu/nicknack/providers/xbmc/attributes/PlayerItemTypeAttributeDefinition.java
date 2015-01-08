package com.github.kmbulebu.nicknack.providers.xbmc.attributes;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.github.kmbulebu.nicknack.core.attributes.BasicAttributeDefinition;
import com.github.kmbulebu.nicknack.core.units.StringUnit;

public class PlayerItemTypeAttributeDefinition extends BasicAttributeDefinition {
	
	public static final PlayerItemTypeAttributeDefinition INSTANCE = new PlayerItemTypeAttributeDefinition();

	public PlayerItemTypeAttributeDefinition() {
		super(UUID.fromString("01309847-cd0f-4170-b0cc-3010dbf1f566"), "Media Type", StringUnit.INSTANCE, false);
	}
	
	public static final Map<String, String> VALUES = new HashMap<>();
	
	static {
		VALUES.put("unknown", "Unknown");
		VALUES.put("movie", "Movie");
		VALUES.put("episode", "Episode");
		VALUES.put("musicivideo", "Music Video");
		VALUES.put("song", "Song");
		VALUES.put("picture", "Picture");
		VALUES.put("channel", "TV Channel");
	};

}
