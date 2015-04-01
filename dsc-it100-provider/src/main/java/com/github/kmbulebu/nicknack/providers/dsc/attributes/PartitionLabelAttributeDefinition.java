package com.github.kmbulebu.nicknack.providers.dsc.attributes;

import java.util.List;
import java.util.UUID;

import com.github.kmbulebu.nicknack.core.attributes.BasicAttributeDefinition;
import com.github.kmbulebu.nicknack.core.providers.Provider;
import com.github.kmbulebu.nicknack.core.valuetypes.ValueChoices;
import com.github.kmbulebu.nicknack.core.valuetypes.builder.ValueTypeBuilder;
import com.github.kmbulebu.nicknack.core.valuetypes.impl.text.Text;
import com.github.kmbulebu.nicknack.providers.dsc.DscProvider;

public class PartitionLabelAttributeDefinition extends BasicAttributeDefinition<Text, String> {
	
	public static final PartitionLabelAttributeDefinition INSTANCE = new PartitionLabelAttributeDefinition();

	public PartitionLabelAttributeDefinition() {
		super(UUID.fromString("639d4cf6-48a3-4979-88dd-94802b19b2b1"), 
				"Partition Label",
				ValueTypeBuilder.text().build(),
				new ValueChoices<String>() {

					@Override
					public List<String> getValueChoices(Provider provider) {
						final DscProvider dscProvider = (DscProvider) provider;
						return dscProvider.getPartitionLabels();
					}
				},
				true,
				false);
	}

}
