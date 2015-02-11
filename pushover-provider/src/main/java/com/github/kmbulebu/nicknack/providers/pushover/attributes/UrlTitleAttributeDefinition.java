package com.github.kmbulebu.nicknack.providers.pushover.attributes;

import java.util.UUID;

import com.github.kmbulebu.nicknack.core.attributes.BasicAttributeDefinition;
import com.github.kmbulebu.nicknack.core.valuetypes.TextType;

public class UrlTitleAttributeDefinition extends BasicAttributeDefinition<TextType> {

	public static final UUID DEF_UUID = UUID.fromString("94d43959-581c-483b-9301-e2b1b09bc7a2");
	
	public static final UrlTitleAttributeDefinition INSTANCE = new UrlTitleAttributeDefinition();

	public UrlTitleAttributeDefinition() {
		super(DEF_UUID, "Url Title", new TextType(), false);
	}

}
