package com.github.kmbulebu.nicknack.server.services.impl;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.annotation.PreDestroy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.kmbulebu.nicknack.core.actions.Action;
import com.github.kmbulebu.nicknack.core.actions.ActionFailureException;
import com.github.kmbulebu.nicknack.core.actions.ActionParameterException;
import com.github.kmbulebu.nicknack.core.providers.ProviderService;
import com.github.kmbulebu.nicknack.server.Application;
import com.github.kmbulebu.nicknack.server.services.ActionQueueService;
import com.github.kmbulebu.nicknack.server.services.ActionsService;
import com.github.kmbulebu.nicknack.server.services.NickNackServerProvider;

@Service
public class ActionQueueServiceImpl implements ActionQueueService {
	
	private static final Logger LOG = LogManager.getLogger(Application.APP_LOGGER_NAME);
	
	@Autowired
	private ProviderService providerService;
	
	@Autowired
	private NickNackServerProvider nickNackServerProvider;
	
	@Autowired
	private ActionsService actionsService;
	
	final private ExecutorService actionsExecutor;
	
	public ActionQueueServiceImpl() {
		actionsExecutor = Executors.newFixedThreadPool(10);
	}
	
	public void enqueueAction(final Action action) {
		final String actionName = providerService.getActionDefinitions().get(action.getAppliesToActionDefinition()).getName();
		final String actionUuid = action.getAppliesToActionDefinition().toString();
		
		actionsExecutor.execute(new Runnable() {

			@Override
			public void run() {
				try {
					providerService.run(action);
					nickNackServerProvider.fireActionCompletedEvent(actionUuid, actionName);
				} catch (ActionFailureException | ActionParameterException e) {
					LOG.warn("Failed to perform action. " + e.getMessage() + " " + action);
					nickNackServerProvider.fireActionFailedEvent(actionUuid, actionName, e.getMessage());
				} 
			}
		});
	}
	
	@PreDestroy
	private void shutdown() throws InterruptedException {
		actionsExecutor.shutdown();
		actionsExecutor.awaitTermination(30, TimeUnit.SECONDS);
	}

	@Override
	public void enqueueAction(UUID actionUuid) {
		final Action action = actionsService.getAction(actionUuid);
		
		enqueueAction(action);
	}

	@Override
	public List<Action> getQueue() {
		// TODO Implement
		throw new UnsupportedOperationException();
	}

}
