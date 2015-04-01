package com.github.kmbulebu.nicknack.providers.dsc.attributes;

import java.util.List;
import java.util.UUID;

import com.github.kmbulebu.nicknack.core.attributes.BasicAttributeDefinition;
import com.github.kmbulebu.nicknack.core.providers.Provider;
import com.github.kmbulebu.nicknack.core.valuetypes.ValueChoices;
import com.github.kmbulebu.nicknack.core.valuetypes.builder.ValueTypeBuilder;
import com.github.kmbulebu.nicknack.core.valuetypes.impl.wholenumber.WholeNumber;
import com.github.kmbulebu.nicknack.providers.dsc.DscProvider;

public class ZoneNumberAttributeDefinition extends BasicAttributeDefinition<WholeNumber, Integer> {
	
	public static final ZoneNumberAttributeDefinition INSTANCE = new ZoneNumberAttributeDefinition();

	public ZoneNumberAttributeDefinition() {
		super(UUID.fromString("2c85db6a-3702-49ec-8b40-be550724177f"), "Zone Number", 
				ValueTypeBuilder.wholeNumber().min(1).max(128).build(),
				new ValueChoices<Integer>() {

					@Override
					public List<Integer> getValueChoices(Provider provider) {
						final DscProvider dscProvider = (DscProvider) provider;
						return dscProvider.getZoneNumbers();
					}
				}, 
				true,
				false);
	}

}
