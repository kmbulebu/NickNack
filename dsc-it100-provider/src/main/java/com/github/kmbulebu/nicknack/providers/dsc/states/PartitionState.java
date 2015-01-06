package com.github.kmbulebu.nicknack.providers.dsc.states;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.github.kmbulebu.nicknack.core.states.State;
import com.github.kmbulebu.nicknack.core.states.StateDefinition;
import com.github.kmbulebu.nicknack.providers.dsc.attributes.PartitionArmedAttributeDefinition;
import com.github.kmbulebu.nicknack.providers.dsc.attributes.PartitionArmedModeAttributeDefinition;
import com.github.kmbulebu.nicknack.providers.dsc.attributes.PartitionLabelAttributeDefinition;
import com.github.kmbulebu.nicknack.providers.dsc.attributes.PartitionNumberAttributeDefinition;

public class PartitionState implements State {
	
	final Map<UUID, String> attributes = new HashMap<>();

	@Override
	public Map<UUID, String> getAttributes() {
		return Collections.unmodifiableMap(attributes);
	}

	@Override
	public StateDefinition getStateDefinition() {
		return PartitionStateDefinition.INSTANCE; 
	}
	
	public void setPartitionNumber(int partitionNumber) {
		attributes.put(PartitionNumberAttributeDefinition.INSTANCE.getUUID(), Integer.toString(partitionNumber));
	}
	
	public void setPartitionLabel(String label) {
		attributes.put(PartitionLabelAttributeDefinition.INSTANCE.getUUID(), label);
	}
	
	public void setPartitionArmed(boolean isArmed) {
		attributes.put(PartitionArmedAttributeDefinition.INSTANCE.getUUID(), Boolean.toString(isArmed));
	}
	
	public void setPartitionArmedMode(String armedMode) {
		attributes.put(PartitionArmedModeAttributeDefinition.INSTANCE.getUUID(), armedMode);
	}
	
}
