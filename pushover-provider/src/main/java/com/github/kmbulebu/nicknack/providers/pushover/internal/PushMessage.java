package com.github.kmbulebu.nicknack.providers.pushover.internal;

import java.util.Date;

public class PushMessage {

	private String user;
	private String message;
	private String device;
	private String title;
	private String url;
	private String urlTitle;
	private Integer priority;
	private Date timeStamp;
	private String sound;
	
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public void setDevice(String device) {
		this.device = device;
	}
	public String getDevice() {
		return device;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUrlTitle() {
		return urlTitle;
	}
	public void setUrlTitle(String urlTitle) {
		this.urlTitle = urlTitle;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	public Date getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}
	public String getSound() {
		return sound;
	}
	public void setSound(String sound) {
		this.sound = sound;
	}
	
	@Override
	public String toString() {
		return "PushMessage [user=" + user + ", message=" + message + ", device=" + device + ", title=" + title
				+ ", url=" + url + ", urlTitle=" + urlTitle + ", priority=" + priority + ", timeStamp=" + timeStamp
				+ ", sound=" + sound + "]";
	}
	
	

}
