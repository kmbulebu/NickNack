package com.github.kmbulebu.nicknack.core.units;

import java.text.ParseException;

import com.github.kmbulebu.nicknack.core.attributes.filters.Operator;

public interface Unit {

	// Name describing this Unit
	public String getName();

	// Returns null if the operator is not supported by this unit.
	public Boolean evaluate(Operator operator, String operand1, String operand2) throws ParseException;

	// Array of operators that this Unit supports with evaluate
	public Operator[] getSupportedOperators();

}