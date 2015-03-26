package com.github.kmbulebu.nicknack.core.attributes;

import java.util.Collections;
import java.util.List;

import com.github.kmbulebu.nicknack.core.valuetypes.ValueType;
import com.github.kmbulebu.nicknack.core.valuetypes.ValueType.ParseException;
import com.github.kmbulebu.nicknack.core.valuetypes.ValueType.Result;

public class AttributeValueParser {
	
	public <T extends ValueType<U>, U> String toString(AttributeDefinition<T, U> attributeDefinition, Object value) {
		if (value != null && attributeDefinition.getValueType().getValueClass().isInstance(value)) {
			final U castedValue = attributeDefinition.getValueType().getValueClass().cast(value);
			final String toString = attributeDefinition.getValueType().toString(castedValue);
			return toString;
		} else {
			return null;
		}
	}
	
	public <T extends ValueType<U>, U> String[] toStrings(AttributeDefinition<T, U> attributeDefinition, Object... values) {
		if (values == null) {
			return null;
		}
		
		final String[] strings = new String[values.length];
		for (int i = 0; i < values.length; i++) {
			if (values[i] == null || attributeDefinition.getValueType().getValueClass().isInstance(values[i])) {
				final U castedValue = attributeDefinition.getValueType().getValueClass().cast(values[i]);
				strings[i] = attributeDefinition.getValueType().toString(castedValue);
			} else {
				strings[i] = null;
			}
		}
		return strings;
	}
	
	public <T extends ValueType<U>, U> U toObject(AttributeDefinition<T, U> attributeDefinition, String value) throws ParseException, InvalidValueException{
		if (value == null) {
			return null;
		}
		final T valueType = attributeDefinition.getValueType();
		if (valueType.getValueClass().isInstance(value)) {
			// Validate against regex 
			final String isValidRegex = valueType.getIsValidRegEx();
			if (isValidRegex != null && !value.matches(isValidRegex)) {
				throw new ParseException("Value is not a " + valueType.getName());
			}
			final U object = attributeDefinition.getValueType().fromString(value);
			final Result validationResult = valueType.validate(object);
			if (!validationResult.isValid()) {
				throw new InvalidValueException(validationResult.getMessages());
			}
			return object; 
		} else {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public <T extends ValueType<U>, U> U[] toObjects(AttributeDefinition<T, U> attributeDefinition, String... values) throws ParseException, InvalidValueException{
		if (values == null) {
			return null;
		}
		
		Object[] result = new Object[values.length];
		final T valueType = attributeDefinition.getValueType();
		for (int i = 0; i < values.length; i++) {
			if (valueType.getValueClass().isInstance(values[i])) {
				// Validate against regex 
				final String isValidRegex = valueType.getIsValidRegEx();
				if (isValidRegex != null && !values[i].matches(isValidRegex)) {
					throw new ParseException("Value is not a " + valueType.getName());
				}
				final U object = attributeDefinition.getValueType().fromString(values[i]);
				final Result validationResult = valueType.validate(object);
				if (!validationResult.isValid()) {
					throw new InvalidValueException(validationResult.getMessages());
				}
				result[i] = object; 
			} else {
				result[i] = null;
			}
		}
		return (U[]) result;
	}
	
	public static class InvalidValueException extends Exception {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private final List<String> messages;
		
		public InvalidValueException(String message) {
			super("The value is invalid.");
			this.messages = Collections.unmodifiableList(Collections.singletonList(message));
		}
		
		public InvalidValueException(List<String> messages) {
			super("The value is invalid.");
			this.messages = Collections.unmodifiableList(messages);
		}
		
		public List<String> getMessages() {
			return messages;
		}
		
	}

}
