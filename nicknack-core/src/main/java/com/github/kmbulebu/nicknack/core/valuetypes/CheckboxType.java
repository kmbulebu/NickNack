package com.github.kmbulebu.nicknack.core.valuetypes;


public class CheckboxType extends AbstractValueType {

	@Override
	public String getName() {
		return "checkbox";
	}

	@Override
	public String getRegexPattern() {
		return "(true|false|True|False)";
	}

	@Override
	public Validation validate(String value) {
		return alwaysTrueValidation;
	}
	
	private static final Validation alwaysTrueValidation = new Validation() {

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
