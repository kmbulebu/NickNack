package com.github.kmbulebu.nicknack.core.valuetypes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractValueType<T extends Serializable> implements ValueType<T> {
	
	@Override
	public List<String> save(List<T> settingValues) {
		final List<String> stringList = new ArrayList<>(settingValues.size());
		for (int i = 0 ; i < settingValues.size() ; i++) {
			stringList.add(i, toString(settingValues.get(i)));
		}
		return stringList;
	}

	@Override
	public List<T> load(List<String> savedData) {
		final List<T> valueList = new ArrayList<T>(savedData.size());
		for (int i = 0; i < savedData.size(); i++) {
			valueList.add(i, fromString(savedData.get(i)));
		}
		return valueList;
	}
	
	protected boolean evaluateInOperand(Object operand1, Object[] operand2) {
		for (Object operand : operand2) {
			if (operand1.equals(operand)) {
				return true;
			}
		}
		return false;
	}
	
	protected boolean evaluateNotInOperand(Object operand1, Object[] operand2) {
		for (Object operand : operand2) {
			if (operand1.equals(operand)) {
				return false;
			}
		}
		return true;
	}

}
