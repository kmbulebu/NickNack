package valuetypes;

import java.io.Serializable;
import java.util.List;

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
	 * Converts or serializes the settingValue to a representation suitable for saving in a configuration file.
	 * @return String suitable for writing to a configuration file.
	 */
	public String save(Object settingValue);
	
	/**
	 * Converts or de-serializes a settingValue stored in the configuration file to a strong typed representation.
	 * @param savedData String representation of the setting value, created by save().
	 * @return ValueType The typed value sutiable for consumption by the provider.
	 */
	public T load(String savedData);
}
