package com.github.kmbulebu.nicknack.providers.pushover;

import java.util.Arrays;

public class PushOverException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7610944602977722525L;
	
	private final String request;
	private final String[] errors;
	private final int status;
	private final int responseCode;
	
	public PushOverException(String request, String[] errors, int status, int responseCode) {
		super(buildMessage(request, errors, status, responseCode));
		this.request = request;
		this.errors = errors;
		this.status = status;
		this.responseCode = responseCode;
	}

	public String getRequest() {
		return request;
	}

	public String[] getErrors() {
		return errors;
	}
	
	public int getStatus() {
		return status;
	}

	public int getResponseCode() {
		return responseCode;
	}
	
	protected static String buildMessage(String request, String[] errors, int status, int responseCode) {
		final StringBuilder builder = new StringBuilder();
		builder.append("PushOver returned status=" + status + " with errors=" + Arrays.toString(errors) + " for request=" + request);
		return builder.toString();
	}
	

}
