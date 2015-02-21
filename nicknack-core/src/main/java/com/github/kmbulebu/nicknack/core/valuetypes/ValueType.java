package com.github.kmbulebu.nicknack.core.valuetypes;

import java.io.Serializable;
import java.util.List;

import com.github.kmbulebu.nicknack.core.attributes.filters.Operator;

/**
 * 
 *
 * @param <T> Class that will represent a value for this type of value.
 */
public interface ValueType<T extends Serializable> {
	
	public Class<T> getTypeClass();
	
	public String getName();
	
	/**
	 * Tests if the specified value is valid. 
	 * 
	 * @param input User selected or entered value.
	 * @return boolean True if the value is valid and accepted. False otherwise.
	 */
	public boolean isValid(T input);

	/**
	 * Converts or serializes the List of values to a representation suitable for saving in a configuration file.
	 * @param settingValues List of values
	 * @return List of String representations
	 */
	public List<String> save(List<T> settingValues);
	
	/**
	 * Converts or de-serializes a List of settings stored in the configuration file to a strong typed representation.
	 * @param savedData List of String representations
	 * @return List of values
	 */
	public List<T> load(List<String> savedData);
	
	/**
	 * Converts or serializes the settingValue to a representation suitable for showing the user.
	 * @return String suitable for writing to a configuration file.
	 */
	public String toString(Object settingValue);
	
	/**
	 * Converts a settingValue entered by a user or stored in the configuration file to a strong typed representation.
	 * @param savedData String representation of the setting value, created by a user or by toString().
	 * @return ValueType The typed value sutiable for consumption by the provider.
	 */
	public T fromString(String savedData);
	
	/**
	 * 
	 * @param operator The operator
	 * @param operand1 First operand in the expression.
	 * @param operand2 Second operand in the expression.
	 * @return Boolean True if the expression operand1 OPERATOR operand2 evalutes to true. False otherwises. 
	 */
	public boolean evaluate(Operator operator, Object operand1, Object operand2);
	
	public boolean evaluate(Operator operator, Object operand1, Object[] operand2);
	
	/**
	 * An array of operators supported by this ValueType
	 * @return Operator[] 
	 */
	public Operator[] getSupportedOperators();

}
