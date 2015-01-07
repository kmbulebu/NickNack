package com.github.kmbulebu.nicknack.providers.ssh;

import java.util.Properties;
import java.util.UUID;

import com.github.kmbulebu.nicknack.core.actions.Action;
import com.github.kmbulebu.nicknack.core.actions.ActionFailureException;
import com.github.kmbulebu.nicknack.core.actions.ActionParameterException;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class ExecuteShellCommandActionDefinition extends SshActionDefinition {
	
	public static final UUID DEF_UUID = UUID.fromString("b7b2ead7-f723-4041-9014-5c7439cfba04");

	public ExecuteShellCommandActionDefinition() {
		super(DEF_UUID, "SSH: Execute Shell Command",
				CommandLineAttributeDefinition.INSTANCE,
				SuccessfulReturnCodeAttributeDefinition.INSTANCE);
	}

	@Override
	public void run(Action action) throws ActionFailureException, ActionParameterException {
		final String host = action.getAttributes().get(HostAttributeDefinition.DEF_UUID);
		final String portStr = action.getAttributes().get(PortAttributeDefinition.DEF_UUID);
		final String commandLine = action.getAttributes().get(CommandLineAttributeDefinition.DEF_UUID);
		final String returnCodeStr = action.getAttributes().get(SuccessfulReturnCodeAttributeDefinition.DEF_UUID);
		final String userName = action.getAttributes().get(UserNameAttributeDefinition.DEF_UUID);
		final String password = action.getAttributes().get(PasswordAttributeDefinition.DEF_UUID);
		
		if (host == null || host.isEmpty()) {
			throw new ActionParameterException("Host parameter is missing.");
		}
		
		if (userName == null || userName.isEmpty()) {
			throw new ActionParameterException("UserName parameter is missing.");
		}
		
		if (password == null || password.isEmpty()) {
			throw new ActionParameterException("Password parameter is missing.");
		}
		
		if (commandLine == null || commandLine.isEmpty()) {
			throw new ActionParameterException("Command Line parameter is missing.");
		}
		
		int port;
		if (portStr == null || !portStr.matches("\\d+")) {
			port = 22;
		} else {
			port = Integer.parseInt(portStr);
		}
		
		int expectedReturnCode;
		if (returnCodeStr == null || !returnCodeStr.matches("\\d+")) {
			expectedReturnCode = 0;
		} else {
			expectedReturnCode = Integer.parseInt(returnCodeStr);
		}
		final JSch jsch = new JSch();
		final Properties config = new Properties(); 
		// TODO Come up with a more secure way of handling initial host key verification.
		config.put("StrictHostKeyChecking", "no");
		Session session;
		
		try {
			session = jsch.getSession(userName, host, port);
		} catch (JSchException e) {
			throw new ActionFailureException("Could not configure SSH client.", e);
		}
		
		session.setConfig(config);
		session.setPassword(password);
		try {
			session.connect();
		} catch (JSchException e) {
			throw new ActionFailureException("Could not connect to SSH server:" + e.getMessage(), e);
		}

		ChannelExec channelExec;
        try {
			channelExec = (ChannelExec) session.openChannel("exec");
		} catch (JSchException e) {
			throw new ActionFailureException("Could not open a channel with SSH server:" + e.getMessage(), e);
		}
        
        channelExec.setCommand(commandLine);
        try {
			channelExec.connect();
		} catch (JSchException e) {
			throw new ActionFailureException("Could not execute command on SSH server:" + e.getMessage(), e);
		}
        
        int rc = -1;
        try {
	        do {
	        	rc = channelExec.getExitStatus();
	        	Thread.sleep(100);
	        } while (rc == -1);
	    } catch (InterruptedException e) {
        	throw new ActionFailureException("Interrupted before command completed.", e);
        } finally {
        	channelExec.disconnect();
        	session.disconnect();
        }
        
        if (rc != expectedReturnCode) {
        	throw new ActionFailureException("Command completed with return code " + rc);
        }
	}

}
