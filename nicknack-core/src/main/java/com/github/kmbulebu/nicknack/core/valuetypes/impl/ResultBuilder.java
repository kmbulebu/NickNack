package com.github.kmbulebu.nicknack.core.valuetypes.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.github.kmbulebu.nicknack.core.valuetypes.ValueType.Result;

public class ResultBuilder {
	
	private List<String> messages = new ArrayList<String>();
	
	public ResultBuilder withMessage(String message) {
		this.messages.add(message);
		return this;
	}
	
	public Result build() {
		final List<String> immutableMessages = Collections.unmodifiableList(messages);
		final boolean isValid = immutableMessages.isEmpty();
		
		return new Result() {
			
			@Override
			public boolean isValid() {
				return isValid;
			}
			
			@Override
			public List<String> getMessages() {
				return immutableMessages;
			}
		};
	}

}
