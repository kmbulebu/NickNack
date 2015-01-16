package com.github.kmbulebu.nicknack.core.providers.settings;


public class HostnameType extends TextType {
	
	private static final String HOST_IP_REGEX = "^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)(\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)){3})$|^((([a-zA-Z0-9]|[a-zA-Z0-9][a-zA-Z0-9\\-]*[a-zA-Z0-9])\\.)*([A-Za-z0-9]|[A-Za-z0-9][A-Za-z0-9\\-]*[A-Za-z0-9]))$";
	
	@Override
	public String getName() {
		return "hostname";
	}
	
	@Override
	public String getRegexPattern() {
		return HOST_IP_REGEX;
	}
	
	@Override
	public void setRegexPattern(String regexPattern) {
		throw new UnsupportedOperationException("regexPattern is immutable.");
	}

}
