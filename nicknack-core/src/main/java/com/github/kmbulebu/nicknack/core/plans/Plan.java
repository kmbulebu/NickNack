package com.github.kmbulebu.nicknack.core.plans;

import java.util.List;

import com.github.kmbulebu.nicknack.core.actions.Action;
import com.github.kmbulebu.nicknack.core.events.filters.EventFilter;
import com.github.kmbulebu.nicknack.core.states.filters.StateFilter;

/**
 * User created Plan.
 * 
 * Basic unit of automation in NickNack.
 * 
 * When an Event is created that matches at least one of the EventFilters
 * 
 * AND
 * 
 * at least one State matches at least one StateFilter
 * 
 * THEN
 * 
 * run the specified Actions in order.
 */
public interface Plan {
	
	/**
	 * 
	 * @return User given name of the Plan
	 */
	public String getName();
	
	/**
	 * 
	 * @return List of EventFilters. At least one of these must match an event for the Action to run.
	 */
	public List<EventFilter> getEventFilters();
	
	/**
	 * 
	 * @return List of StateFilters. Optional. At least one of these must match a state for the Action to run.
	 */
	public List<StateFilter> getStateFilters();
	
	/**
	 * 
	 * @return List of Actions to run, in sequence, when the filters match an Event and optionally States.
	 */
	public List<Action> getActions();

}
