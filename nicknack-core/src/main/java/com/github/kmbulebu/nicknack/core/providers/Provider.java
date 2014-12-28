package com.github.kmbulebu.nicknack.core.providers;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.configuration.Configuration;

import com.github.kmbulebu.nicknack.core.actions.Action;
import com.github.kmbulebu.nicknack.core.actions.ActionDefinition;
import com.github.kmbulebu.nicknack.core.actions.ActionFailureException;
import com.github.kmbulebu.nicknack.core.actions.ActionParameterException;
import com.github.kmbulebu.nicknack.core.events.EventDefinition;
import com.github.kmbulebu.nicknack.core.states.State;
import com.github.kmbulebu.nicknack.core.states.StateDefinition;

/**
 * Provider Interface. Main entry point for extending NickNack. 
 * 
 * Implement to define new sets of actions and events. 
 * @author kmbulebu
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
	 * 
	 * Key is the value, Value is the displayName
	 * 
	 * @return A Map of possible values for the given attribute definition or null if the given
	 * attribute does not have a discrete list of possible values. Used only for
	 * populating a list of choices to the user while creating attribute filters. 
	 */
	// Should we use UUID or attribute definition here?
	public Map<String, String> getAttributeDefinitionValues(UUID eventDefinitionUuid, UUID attributeDefinitionUuid);
	
	public void run(Action action) throws ActionFailureException, ActionParameterException;
	
	/**
	 * Initializes the provider. Called when NickNack finds the provider, but before it interrogates for event definitions and action definitions.
	 * @throws Exception
	 */
	// TODO Pass in a configuration object that tells the provide where to store config files, what version of nicknack, etc.
	public void init(Configuration configuration, OnEventListener onEventListener) throws Exception;
	
	// TODO public void onConfigurationChange(Configuration configuration);
	
}
