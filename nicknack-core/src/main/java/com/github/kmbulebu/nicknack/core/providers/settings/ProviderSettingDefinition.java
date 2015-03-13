package com.github.kmbulebu.nicknack.core.providers.settings;

import java.util.List;

/**
 * A single setting that may have one or many values.
 * 
 * At present, providers may only use or extend the implementations available from NickNack core.
 * 
 *
 * @param <ValueType> Class that will represent a value for this setting.
 */
public interface ProviderSettingDefinition<ValueType> {
	
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
	 * Converts or serializes the settingValue to a representation suitable for saving in a configuration file.
	 * @return String suitable for writing to a configuration file.
	 */
	public String save(ValueType settingValue);
	
	/**
	 * Converts or de-serializes a settingValue stored in the configuration file to a strong typed representation.
	 * @param savedData String representation of the setting value, created by save().
	 * @return ValueType The typed value sutiable for consumption by the provider.
	 */
	public ValueType load(String savedData);
	
	/**
	 * Tests if the specified value is valid. 
	 * 
	 * getDescription() must provide adequate guidance to the user as to what is valid or not.
	 * @param value User selected or entered value.
	 * @return boolean True if the value is valid and accepted. False otherwise.
	 */
	public boolean isValid(ValueType value);
	
	/**
	 * Provides a multiple choice list of values for this Setting.
	 * 
	 * @return List A static list of values that the user may choose from for this Setting or null if the user may supply their own values.
	 */
	public List<ValueType> getValueChoices();
	
	/**
	 * True if this setting may have more than one value.
	 * @return boolean True if this setting may have more than one value.
	 */
	public boolean isArray();
	
	/**
	 * Converts or serializes the List of values to a representation suitable for saving in a configuration file.
	 * @param settingValues List of values
	 * @return List of String representations
	 */
	public List<String> save(List<ValueType> settingValues);
	
	/**
	 * Converts or de-serializes a List of settings stored in the configuration file to a strong typed representation.
	 * @param savedData List of String representations
	 * @return List of values
	 */
	public List<ValueType> load(List<String> savedData);

	// TODO Do we need a way to convert to/from HTML representations? Not yet, NickNack core will provide base representations to start that are renderable.
	// TODO Do we allow setting groups? Setting B is only available if Setting A is defined?
}
