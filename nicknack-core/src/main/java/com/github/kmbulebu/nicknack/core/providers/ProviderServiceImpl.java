package com.github.kmbulebu.nicknack.core.providers;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import rx.Observable;
import rx.Subscriber;
import rx.observables.ConnectableObservable;

import com.github.kmbulebu.nicknack.core.actions.Action;
import com.github.kmbulebu.nicknack.core.actions.ActionDefinition;
import com.github.kmbulebu.nicknack.core.actions.ActionFailureException;
import com.github.kmbulebu.nicknack.core.actions.ActionParameterException;
import com.github.kmbulebu.nicknack.core.events.Event;
import com.github.kmbulebu.nicknack.core.events.EventDefinition;
import com.github.kmbulebu.nicknack.core.states.StateDefinition;

public class ProviderServiceImpl implements ProviderService, OnEventListener, rx.Observable.OnSubscribe<Event> {
	
	private static final Logger LOG = LogManager.getLogger();
	
	private static ProviderServiceImpl service;
    private ProviderLoader loader;
    
    private final Map<UUID, EventDefinition> eventDefinitions = new HashMap<UUID, EventDefinition>();
    private final Map<UUID, StateDefinition> stateDefinitions = new HashMap<UUID, StateDefinition>();
    private final Map<UUID, ActionDefinition> actionDefinitions = new HashMap<UUID, ActionDefinition>();
    private final Map<UUID, Provider> providers = new HashMap<UUID, Provider>();
    private final Map<UUID, UUID> eventDefinitionToProvider = new HashMap<UUID, UUID>();
    private final Map<UUID, UUID> stateDefinitionToProvider = new HashMap<UUID, UUID>();
    private final Map<UUID, UUID> actionDefinitionToProvider = new HashMap<UUID, UUID>();
    
    
    private ConnectableObservable<Event> eventStream;
    private Subscriber<? super Event> subscriber;
    
    private final XMLConfiguration configuration;
    
    private ProviderServiceImpl(final Path providersDirectory, final XMLConfiguration configuration) {
    	this.configuration = configuration;
        loader = new ProviderLoader(providersDirectory);
        eventStream = Observable.create(this).publish();
        eventStream.connect();
        // TODO Do something smart with the errors.
        initializeProviders();
    }

    public static synchronized ProviderServiceImpl getInstance(final Path providersDirectory, final XMLConfiguration configuration) {
    	if (LOG.isTraceEnabled()) {
    		LOG.entry(configuration);
    	}
        if (service == null) {
            service = new ProviderServiceImpl(providersDirectory, configuration);
        }
        if (LOG.isTraceEnabled()) {
        	LOG.exit(service);
        }
        return service;
    }
    
    /* (non-Javadoc)
	 * @see com.oakcity.nicknack.core.providers.ProviderService#getActionDefinitions()
	 */
    @Override
	public Map<UUID, ActionDefinition> getActionDefinitions() {
    	return Collections.unmodifiableMap(actionDefinitions);
    }
    
    /* (non-Javadoc)
	 * @see com.oakcity.nicknack.core.providers.ProviderService#getEventDefinitions()
	 */
    @Override
	public Map<UUID, EventDefinition> getEventDefinitions() {
    	return Collections.unmodifiableMap(eventDefinitions);
    }
    
    @Override
	public Map<UUID, StateDefinition> getStateDefinitions() {
    	return Collections.unmodifiableMap(stateDefinitions);
	}
    
    @Override
    public Map<UUID, Provider> getProviders() {
    	return Collections.unmodifiableMap(providers);
    }
    
    protected List<Exception> initializeProviders() {
    	if (LOG.isTraceEnabled()) {
    		LOG.entry();
    	}
    	
    	Iterator<Provider> providers;
		try {
			providers = loader.loadProviders().iterator();
		} catch (IOException e) {
			LOG.error("Could not load providers. " + e.getMessage(), e);
			return Collections.emptyList();
		}
    	
    	Provider provider = null;
    	List<Exception> errors = new ArrayList<Exception>();
    	if (LOG.isInfoEnabled()) {
    		LOG.info("Loading providers.");
    	}
		while (providers.hasNext()) {
			provider = providers.next();
			errors.addAll(initializeProvider(provider));
		}
		
		return errors;
    	
    }
    
    protected List<Exception> initializeProvider(Provider provider) {
    	List<Exception> errors = new ArrayList<Exception>();
    	
    	try {
			if (LOG.isInfoEnabled()) {
				LOG.info("Found provider: " + provider.getName() + " v" + provider.getVersion() + " by " + provider.getAuthor());
			}
			this.providers.put(provider.getUuid(), provider);
			final String configKey = "providers.uuid" + provider.getUuid().toString().replaceAll("-", "");
			if (!configuration.containsKey(configKey + ".name")) {
				configuration.addProperty(configKey + ".name", provider.getName());
			}
			final Configuration providerConfig = configuration.configurationAt(configKey, true);
			
			boolean disabled = providerConfig.getBoolean("disabled", false);
			
			if (disabled) {
				if (LOG.isInfoEnabled()) {
					LOG.info("Per configuration, disabling provider: " + provider.getName() + " v" + provider.getVersion() + " by " + provider.getAuthor());
				}
			} else {
			
				provider.init(providerConfig, this);
				
				if (provider.getEventDefinitions() != null) {
					for (EventDefinition eventDef : provider.getEventDefinitions()) {
						UUID uuid = eventDef.getUUID();
						if (uuid == null) {
							LOG.error("Provider, " + provider.getName() + " (" + provider.getUuid() + ") has an Event Definition with null UUID.");
						} else {
							eventDefinitions.put(uuid, eventDef);
							eventDefinitionToProvider.put(uuid, provider.getUuid());
						}
					}
				}
				
				if (provider.getStateDefinitions() != null) {
					for (StateDefinition stateDef : provider.getStateDefinitions()) {
						UUID uuid = stateDef.getUUID();
						if (uuid == null) {
							LOG.error("Provider, " + provider.getName() + " (" + provider.getUuid() + ") has an State Definition with null UUID.");
						} else {
							stateDefinitions.put(uuid, stateDef);
							stateDefinitionToProvider.put(uuid, provider.getUuid());
						}
					}
				}
				
				if (provider.getActionDefinitions() != null) {
					for (ActionDefinition actionDef : provider.getActionDefinitions()) {
						UUID uuid = actionDef.getUUID();
						if (uuid == null) {
							LOG.error("Provider, " + provider.getName() + " (" + provider.getUuid() + ") has an Action Definition with null UUID.");
						} else {
							actionDefinitions.put(uuid, actionDef);
							actionDefinitionToProvider.put(uuid, provider.getUuid());
						}
					}
				}
				
			} // If disabled
		} catch (Exception e) {
			LOG.error("Failed to initialize provider, " + provider.getName() + " (" + provider.getUuid() + "):" + e.getMessage(), e);
    		errors.add(e);
    	}
    	
    	return errors;
    }

	@Override
	public void onEvent(Event event) {
		if (LOG.isTraceEnabled()) {
			LOG.entry(event);
		}
		
		if (subscriber != null) {
			subscriber.onNext(event);
		}
		
		if (LOG.isTraceEnabled()) {
			LOG.exit();
		}
	}

	@Override
	public Observable<Event> getEvents() {
		return eventStream;
	}

	@Override
	public void call(Subscriber<? super Event> subscriber) {
		this.subscriber = subscriber;
	}

	@Override
	public void run(Action action) throws ActionFailureException, ActionParameterException {
		if (LOG.isTraceEnabled()) {
			LOG.entry(action);
		}
		
		final UUID actionDefinitionUuid = action.getAppliesToActionDefinition();
		final UUID providerUuid = actionDefinitionToProvider.get(actionDefinitionUuid);
		
		final Provider provider = providers.get(providerUuid);
		
		try {
			provider.run(action);
		} catch (ActionFailureException | ActionParameterException e) {
			LOG.throwing(e);
			throw e;
		} catch (Exception e) {
			// Any unknown or unexpected exceptions are wrapped as a failure and thrown.
			final ActionFailureException afe = new ActionFailureException(e);
			LOG.throwing(afe);
			throw afe;
		}
		
		if (LOG.isTraceEnabled()) {
			LOG.exit();
		}
	}

	@Override
	public Provider getProviderByActionDefinitionUuid(UUID actionDefinitionUuid) {
		UUID providerUuid = actionDefinitionToProvider.get(actionDefinitionUuid);
		
		if (providerUuid == null) {
			return null;
		}
		
		return providers.get(providerUuid);
	}

	@Override
	public Provider getProviderByEventDefinitionUuid(UUID eventDefinitionUuid) {
		UUID providerUuid = eventDefinitionToProvider.get(eventDefinitionUuid);
		
		if (providerUuid == null) {
			return null;
		}
		
		return providers.get(providerUuid);
	}
	
	@Override
	public Provider getProviderByStateDefinitionUuid(UUID stateDefinitionUuid) {
		UUID providerUuid = stateDefinitionToProvider.get(stateDefinitionUuid);
		
		if (providerUuid == null) {
			return null;
		}
		
		return providers.get(providerUuid);
	}

	@Override
	public List<Exception> addProvider(Provider provider) {
		return initializeProvider(provider);
	}

	

}
