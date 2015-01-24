package com.github.kmbulebu.nicknack.core.valuetypes;

import com.github.kmbulebu.nicknack.core.attributes.filters.Operator;


public class CheckboxType extends AbstractValueType<Boolean> {
	
	@Override
	public Class<Boolean> getTypeClass() {
		return Boolean.class;
	}

	@Override
	public String getName() {
		return "checkbox";
	}
	
	@Override
	public boolean isValid(Boolean input) {
		return true;
	}

	@Override
	public String save(Object settingValue) {
		return getTypeClass().cast(settingValue).toString();
	}

	@Override
	public Boolean load(String savedData) {
		return Boolean.parseBoolean(savedData);
	}

	@Override
	public boolean evaluate(Operator operator, Object operand1, Object operand2) {
		switch(operator) {
		case EQUALS:
			return operand1.equals(operand2);
		default:
			return false;
		}
	}
	
	@Override
	public boolean evaluate(Operator operator, Object operand1, Object[] operand2) {
		return false;
	}
	
	@Override
	public Operator[] getSupportedOperators() {
		return new Operator[] {Operator.EQUALS};
	}
	

}
