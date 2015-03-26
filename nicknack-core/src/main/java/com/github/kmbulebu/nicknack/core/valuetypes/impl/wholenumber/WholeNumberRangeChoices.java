package com.github.kmbulebu.nicknack.core.valuetypes.impl.wholenumber;

import java.util.ArrayList;
import java.util.List;

import com.github.kmbulebu.nicknack.core.valuetypes.impl.StaticValueChoices;

public class WholeNumberRangeChoices extends StaticValueChoices<Integer> {
	
	public WholeNumberRangeChoices(int start, int end) {
		this(start, end, 1);
	}
	
	public WholeNumberRangeChoices(int start, int end, int step) {
		super(createIntegerArray(start, end, step));
	}
	
	private static final Integer[] createIntegerArray(int start, int end, int step) {
		final List<Integer> ints = new ArrayList<Integer>();
		for (int i = start; i <= end; i = i + step) {
			ints.add(i);
		}
		return ints.toArray(new Integer[ints.size()]);
	}

}
