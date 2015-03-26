package com.github.kmbulebu.nicknack.core.valuetypes.builder;

import com.github.kmbulebu.nicknack.core.valuetypes.impl.text.Text;

public class TextBuilder {
	
	private Integer minLength = null;
	private Integer maxLength = null;
	private String regex = null;
	private String regexNotMatchMessage = null;
	
	protected TextBuilder() {
		super();
	}
	
	public Text build() {
		return new Text(minLength, maxLength, regex, regexNotMatchMessage);
	}
	
	public TextBuilder minLength(Integer minLength) {
		this.minLength = minLength;
		return this;
	}
	
	public TextBuilder maxLength(Integer maxLength) {
		this.maxLength = maxLength;
		return this;
	}
	
	public TextBuilder regEx(String regEx, String regexNotMatchMessage) {
		this.regex = regEx;
		this.regexNotMatchMessage = regexNotMatchMessage;
		return this;
	}

}
