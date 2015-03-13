package com.github.kmbulebu.nicknack.core.valuetypes;


/**
 * 
 *
 * @param <T> Class that will represent a value for this type of value.
 */
public interface ValueType {
	
	/**
	 * Name of this ValueType. (Number, Email Address, etc).
	 * @return
	 */
	public String getName();
	
	/**
	 * A regular expression that may be used for simple, first step validation of a value.
	 * @return
	 */
	public String getRegexPattern();
	
	/**
	 * Complete validation of a value. Only non-null values that match getRegexPattern() will be passed as input.
	 * 
	 * @param value
	 * @return
	 */
	public Validation validate(String value);
	
	public interface Validation {
		
		public boolean isValid();
		
		public String invalidMessage();
		
	}
	
}
