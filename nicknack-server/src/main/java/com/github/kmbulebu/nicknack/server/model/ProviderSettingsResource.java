package com.github.kmbulebu.nicknack.server.model;

import java.util.List;
import java.util.Map;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

@Relation(value="ProviderSettings", collectionRelation="ProviderSettings")
public class ProviderSettingsResource extends ResourceSupport {
	
	private Map<String, List<?>> settings;
	
	private Map<String, List<String>> errors;
	
	private boolean complete;
	
	private boolean enabled;
	
	public void setSettings(Map<String, List<?>> settings) {
		this.settings = settings;
	}
	
	public Map<String, List<?>> getSettings() {
		return settings;
	}

	public Map<String, List<String>> getErrors() {
		return errors;
	}

	public void setErrors(Map<String, List<String>> errors) {
		this.errors = errors;
	}

	public boolean isComplete() {
		return complete;
	}

	public void setComplete(boolean complete) {
		this.complete = complete;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
 
	
	
}
