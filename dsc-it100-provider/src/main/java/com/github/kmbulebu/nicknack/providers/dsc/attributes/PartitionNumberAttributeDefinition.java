package com.github.kmbulebu.nicknack.providers.dsc.attributes;

import java.util.List;
import java.util.UUID;

import com.github.kmbulebu.nicknack.core.attributes.BasicAttributeDefinition;
import com.github.kmbulebu.nicknack.core.providers.Provider;
import com.github.kmbulebu.nicknack.core.valuetypes.ValueChoices;
import com.github.kmbulebu.nicknack.core.valuetypes.builder.ValueTypeBuilder;
import com.github.kmbulebu.nicknack.core.valuetypes.impl.wholenumber.WholeNumber;
import com.github.kmbulebu.nicknack.providers.dsc.DscProvider;

public class PartitionNumberAttributeDefinition extends BasicAttributeDefinition<WholeNumber, Integer> {

	public static final UUID DEF_UUID = UUID.fromString("5748a2bb-8030-4e17-a325-494ad11e3893");

	public static final PartitionNumberAttributeDefinition INSTANCE = new PartitionNumberAttributeDefinition();

	public PartitionNumberAttributeDefinition() {
		super(DEF_UUID, "Partition Number", ValueTypeBuilder.wholeNumber().min(1).max(16).build(),
				new ValueChoices<Integer>() {

					@Override
					public List<Integer> getValueChoices(Provider provider) {
						final DscProvider dscProvider = (DscProvider) provider;
						return dscProvider.getPartitionNumbers();
					}
				}, true, false);
	}

}
