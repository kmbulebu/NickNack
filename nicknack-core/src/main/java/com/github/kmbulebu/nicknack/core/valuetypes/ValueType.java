package com.github.kmbulebu.nicknack.core.valuetypes;

import java.util.List;

import com.github.kmbulebu.nicknack.core.attributes.filters.Operator;



/**
 * 
 *
 * @param <T> Class that will represent a value for this type of value.
 */
public interface ValueType<T> {
	
	/**
	 * A Java class that will represent the strongly typed version of a value.
	 * @return
	 */
	public Class<T> getValueClass();
	
	/**
	 * A human readable name.
	 * @return
	 */
	public String getName();
	
	/**
	 * A regular expression that validates if a String can be converted to this ValueType.
	 * @return
	 */
	public String getIsValidRegEx();
	
	/**
	 * Validates a strong type value. This is useful for validating that a value is within bounds,
	 * and similar requirements.
	 * @param value
	 * @return
	 */
	public Result validate(T value);
	
	public T fromString(String valueString) throws ParseException;
	
	public String toString(T value);
	
	public Operator[] getSupportedOperators();
	
	public boolean evaluate(T value, Operator operator, T operand);
	
	public boolean evaluateArray(T value, Operator operator, T[] operands);
	
	public interface Result {
		
		public boolean isValid();
		
		public List<String> getMessages();
		
	}
	
	public static class ParseException extends Exception {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public ParseException(String message, Throwable cause) {
			super(message, cause);
			// TODO Auto-generated constructor stub
		}

		public ParseException(String message) {
			super(message);
			// TODO Auto-generated constructor stub
		}

		public ParseException(Throwable cause) {
			super(cause);
			// TODO Auto-generated constructor stub
		}
		
		
		
	}

}