package com.github.kmbulebu.nicknack.providers.pushover;

import java.util.Arrays;

public class PushOverResponse {
	
	private int status;
	private String request;
	private String[] errors;
	private String user;
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getRequest() {
		return request;
	}
	public void setRequest(String request) {
		this.request = request;
	}
	public String[] getErrors() {
		return errors;
	}
	public void setErrors(String[] errors) {
		this.errors = errors;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	@Override
	public String toString() {
		return "PushOverResponse [status=" + status + ", request=" + request + ", errors=" + Arrays.toString(errors)
				+ ", user=" + user + "]";
	}
	
	

}
