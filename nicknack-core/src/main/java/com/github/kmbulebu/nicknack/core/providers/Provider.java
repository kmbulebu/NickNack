package com.github.kmbulebu.nicknack.core.providers;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import com.github.kmbulebu.nicknack.core.actions.ActionDefinition;
import com.github.kmbulebu.nicknack.core.attributes.AttributeCollection;
import com.github.kmbulebu.nicknack.core.attributes.AttributeDefinition;
import com.github.kmbulebu.nicknack.core.events.EventDefinition;
import com.github.kmbulebu.nicknack.core.states.State;
import com.github.kmbulebu.nicknack.core.states.StateDefinition;

/**
 * Provider Interface. Main entry point for extending NickNack. 
 * 
 * Implement to define new sets of Actions, Events, and States.
 *
 */
public interface Provider {
	
	public UUID getUuid();
	
	/**
	 * 
	 * @return User friendly name of this provider. "E.g. (LCD TV Provider)"
	 */
	public String getName();
	
	/**
	 * 
	 * @return User friendly name of person or organization who created this Provider.
	 */
	public String getAuthor();
	
	/**
	 * 
	 * @return Version of this provider. Higher versions are newer than lower versions. 
	 */
	public int getVersion();
	
	/**
	 * 
	 * @return A list of settings available to the user in configuring this provider.
	 */
	public List<AttributeDefinition<?,?>> getSettingDefinitions();
	
	/**
	 * 
	 * @return A list of all possible events this provider may broadcast.
	 */
	public Collection<EventDefinition> getEventDefinitions();
	
	/**
	 * 
	 * @return A list of all possible states that may exist.
	 */
	public Collection<StateDefinition> getStateDefinitions();
	
	/**
	 * 
	 * @return A list of all possible actions this provider may perform.
	 */
	public Collection<ActionDefinition> getActionDefinitions();
	
	/**
	 * 
	 */
	public List<State> getStates(UUID stateDefinitionUuid);
	
	/**
	 * Initializes the provider. Called when NickNack finds the provider, but before it interrogates for event definitions and action definitions.
	 * @throws Exception
	*/
	public void init(AttributeCollection settings,  OnEventListener onEventListener) throws Exception;
	
	public void shutdown() throws Exception;
	
	// TODO public void onConfigurationChange(ProviderConfiguration configuration);
	
}
