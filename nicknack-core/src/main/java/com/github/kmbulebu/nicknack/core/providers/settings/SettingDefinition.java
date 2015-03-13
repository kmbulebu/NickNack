package com.github.kmbulebu.nicknack.core.providers.settings;

import java.util.List;

import com.github.kmbulebu.nicknack.core.valuetypes.ValueType;

/**
 * A single setting that may have one or many values.
 * 
 * At present, providers may only use or extend the implementations available from NickNack core.
 * 
 *
 * @param <T> Class that will represent the type of value for this setting.
 * @param <U> Class that will represent a value for this setting.
 */
public interface SettingDefinition<T extends ValueType> {
	
	/**
	 * Key to find this setting.
	 * 
	 * @return String Key compromised of only [A-Za-z_-] characters.
	 */
	public String getKey();
	
	/**
	 * A human readable name for the Setting. 
	 * @return String Setting's name.
	 */
	public String getName();
	
	/**
	 * Human readable help text to further explain the purpose of the Setting.
	 * @return String Description
	 */
	public String getDescription();
	
	/**
	 * 
	 * @return True if at least one value of this setting is required for the provider to be enabled. False otherwise.
	 */
	public boolean isRequired();

	
	/**
	 * True if this setting may have more than one value.
	 * @return boolean True if this setting may have more than one value.
	 */
	public boolean isArray();

	public T getSettingType();
	
	/**
	 * Provides a multiple choice list of values for this Setting.
	 * 
	 * @return List A static list of values that the user may choose from for this Setting or null if the user may supply their own values.
	 */
	public List<String> getValueChoices();

	// TODO Do we need a way to convert to/from HTML representations? Not yet, NickNack core will provide base representations to start that are renderable.
	// TODO Do we allow setting groups? Setting B is only available if Setting A is defined?
}
