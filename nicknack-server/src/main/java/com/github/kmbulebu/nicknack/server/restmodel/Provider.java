package com.github.kmbulebu.nicknack.server.restmodel;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class Provider {
	
	private UUID uuid;
	private String name;
	private String author;
	private int version;
	
	private List<Setting> settings = new LinkedList<>();
	
	public UUID getUuid() {
		return uuid;
	}



	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getAuthor() {
		return author;
	}



	public void setAuthor(String author) {
		this.author = author;
	}



	public int getVersion() {
		return version;
	}



	public void setVersion(int version) {
		this.version = version;
	}



	public List<Setting> getSettings() {
		return settings;
	}



	public void setSettings(List<Setting> settings) {
		this.settings = settings;
	}



	public static class Setting {
		
		private UUID uuid;
		private String name;
		private String description;
		private boolean required;
		private boolean multiValue;
		private String[] values = new String[]{};
		
		public UUID getUuid() {
			return uuid;
		}
		public void setUuid(UUID uuid) {
			this.uuid = uuid;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public boolean isRequired() {
			return required;
		}
		public void setRequired(boolean required) {
			this.required = required;
		}
		public boolean isMultiValue() {
			return multiValue;
		}
		public void setMultiValue(boolean multiValue) {
			this.multiValue = multiValue;
		}
		public String[] getValues() {
			return values;
		}
		public void setValues(String[] values) {
			this.values = values;
		}
		
	}

}
