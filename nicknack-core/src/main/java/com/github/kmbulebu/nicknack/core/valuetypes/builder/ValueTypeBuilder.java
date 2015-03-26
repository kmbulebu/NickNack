package com.github.kmbulebu.nicknack.core.valuetypes.builder;


public class ValueTypeBuilder {
	
	public static WholeNumberBuilder wholeNumber() {
		return new WholeNumberBuilder();
	}
	
	public static TextBuilder text() {
		return new TextBuilder();
	}
	
	public static OnOffBuilder onOff() {
		return new OnOffBuilder();
	}

}
