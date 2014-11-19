package com.github.kmbulebu.nicknack.server.services;


public interface NickNackServerProvider {

	public void fireActionCompletedEvent(String actionDefUuid, String actionDefName);
	
	public void fireActionFailedEvent(String actionDefUuid, String actionDefName, String errorMessage);
		

}