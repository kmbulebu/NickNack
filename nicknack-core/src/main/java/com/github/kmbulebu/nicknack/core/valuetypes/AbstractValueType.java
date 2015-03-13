package com.github.kmbulebu.nicknack.core.valuetypes;


public abstract class AbstractValueType implements ValueType {
	
	@Override
	public Validation validate(String value) {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected Validation buildValid() {
		return new Validation() {
			
			@Override
			public boolean isValid() {
				return true;
			}
			
			@Override
			public String invalidMessage() {
				return null;
			}
		};
	}
	
	protected Validation buildInvalid(final String message) {
		return new Validation() {
			
			@Override
			public boolean isValid() {
				return false;
			}
			
			@Override
			public String invalidMessage() {
				return message;
			}
		};
	}
	
	
}
