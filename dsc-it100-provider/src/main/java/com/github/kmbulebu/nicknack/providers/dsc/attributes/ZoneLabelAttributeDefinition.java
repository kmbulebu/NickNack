package com.github.kmbulebu.nicknack.providers.dsc.attributes;

import java.util.List;
import java.util.UUID;

import com.github.kmbulebu.nicknack.core.attributes.BasicAttributeDefinition;
import com.github.kmbulebu.nicknack.core.providers.Provider;
import com.github.kmbulebu.nicknack.core.valuetypes.ValueChoices;
import com.github.kmbulebu.nicknack.core.valuetypes.builder.ValueTypeBuilder;
import com.github.kmbulebu.nicknack.core.valuetypes.impl.text.Text;
import com.github.kmbulebu.nicknack.providers.dsc.DscProvider;

public class ZoneLabelAttributeDefinition extends BasicAttributeDefinition<Text, String>  {
	
	public static final ZoneLabelAttributeDefinition INSTANCE = new ZoneLabelAttributeDefinition();

	public ZoneLabelAttributeDefinition() {
		super(UUID.fromString("faa115a0-8e57-4fad-9f94-5c5c8f5d860b"), 				
				"Zone Label",
				ValueTypeBuilder.text().build(),
				new ValueChoices<String>() {

					@Override
					public List<String> getValueChoices(Provider provider) {
						final DscProvider dscProvider = (DscProvider) provider;
						return dscProvider.getZoneLabels();
					}
				},
				true,
				false);
	}

}
