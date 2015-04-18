package com.github.kmbulebu.nicknack.server.restmodel;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonView;

public class Provider {
	
	@JsonView(View.Summary.class)
	private UUID uuid;
	
	@JsonView(View.Summary.class)
	private String name;
	
	@JsonView(View.Summary.class)
	private String author;
	
	@JsonView(View.Summary.class)
	private int version;
	
	// TODO Separate the definition from the values, like state and actions.
	private List<Attribute> settings = new LinkedList<>();
	
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



	public List<Attribute> getSettings() {
		return settings;
	}



	public void setSettings(List<Attribute> settings) {
		this.settings = settings;
	}

}
