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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import rx.Observable;
import rx.Subscriber;
import rx.observables.ConnectableObservable;

import com.github.kmbulebu.nicknack.core.actions.Action;
import com.github.kmbulebu.nicknack.core.actions.ActionDefinition;
import com.github.kmbulebu.nicknack.core.actions.ActionFailureException;
import com.github.kmbulebu.nicknack.core.actions.ActionAttributeException;
import com.github.kmbulebu.nicknack.core.attributes.AttributeCollection;
import com.github.kmbulebu.nicknack.core.attributes.AttributeDefinition;
import com.github.kmbulebu.nicknack.core.events.Event;
import com.github.kmbulebu.nicknack.core.events.EventDefinition;
import com.github.kmbulebu.nicknack.core.states.StateDefinition;

public class ProviderServiceImpl implements ProviderService, OnEventListener, rx.Observable.OnSubscribe<Event> {
	
	private static final Logger LOG = LogManager.getLogger();
	
	private static ProviderServiceImpl INSTANCE;
    private ProviderLoader loader;
    
    private final Map<UUID, EventDefinition> eventDefinitions = new HashMap<>();
    private final Map<UUID, StateDefinition> stateDefinitions = new HashMap<>();
    private final Map<UUID, ActionDefinition> actionDefinitions = new HashMap<>();
    private final Map<UUID, AttributeDefinition<?, ?>> attributeDefinitions = new HashMap<>();
    private final Map<UUID, Provider> providers = new HashMap<>();
    private final Map<UUID, UUID> eventDefinitionToProvider = new HashMap<>();
    private final Map<UUID, UUID> stateDefinitionToProvider = new HashMap<>();
    private final Map<UUID, UUID> actionDefinitionToProvider = new HashMap<>();
    private final Map<UUID, AttributeCollection> providerToSettings = new HashMap<>();
    
    
    private ConnectableObservable<Event> eventStream;
    private Subscriber<? super Event> subscriber;
    
    private ProviderServiceImpl(final Path providersDirectory) {
    	loader = new ProviderLoader(providersDirectory);
    	loadProviders();
    }

    public static synchronized ProviderServiceImpl getInstance(final Path providersDirectory) {
    	if (LOG.isTraceEnabled()) {
    		LOG.entry(providersDirectory);
    	}
        if (INSTANCE == null) {
        	INSTANCE = new ProviderServiceImpl(providersDirectory);
        }
        if (LOG.isTraceEnabled()) {
        	LOG.exit(INSTANCE);
        }
        return INSTANCE;
    }
    
    @Override
    public void initialize() {
		  eventStream = Observable.create(this).publish();
		  eventStream.connect();
		  // TODO Do something smart with the errors.
		  initializeProviders();
    }

    
    @Override
    public Map<UUID, Provider> getProviders() {
    	return Collections.unmodifiableMap(providers);
    }
    
    protected List<Exception> initializeProviders() {
    	if (LOG.isTraceEnabled()) {
    		LOG.entry();
    	}
    	
    	List<Exception> errors = new ArrayList<Exception>();
    	if (LOG.isInfoEnabled()) {
    		LOG.info("Initializing providers.");
    	}
    	
    	for (Provider provider : this.providers.values()) {
			errors.addAll(initializeProvider(provider));
		}
		
    	if (LOG.isTraceEnabled()) {
    		LOG.exit(errors);
    	}
		return errors;
    }
    
    protected void loadProviders() {
    	if (LOG.isTraceEnabled()) {
    		LOG.entry();
    	}
    	
    	Iterator<Provider> providers;
		try {
			providers = loader.loadProviders().iterator();
		} catch (IOException e) {
			LOG.error("Could not load providers. " + e.getMessage(), e);
			return;
		}
    	
		Provider provider = null;
    	while (providers.hasNext()) {
			provider = providers.next();
			this.providers.put(provider.getUuid(), provider);
			addAttributeDefinitions(provider.getSettingDefinitions());
		}
    	

    	if (LOG.isInfoEnabled()) {
    		LOG.info("Loaded providers.");
    	}
    }
    
	protected List<Exception> initializeProvider(Provider provider) {
    	List<Exception> errors = new ArrayList<Exception>();
    	
    	try {
			if (LOG.isInfoEnabled()) {
				LOG.info("Found provider: " + provider.getName() + " v" + provider.getVersion() + " by " + provider.getAuthor());
			}
				final AttributeCollection providerSettings = providerToSettings.get(provider.getUuid());
				if (providerSettings == null) {
					LOG.warn("Disabling provider due to incomplete settings: " + provider.getName() + " v" + provider.getVersion() + " by " + provider.getAuthor());
				} else {
					provider.init(providerSettings, this);
					
					if (provider.getEventDefinitions() != null) {
						for (EventDefinition eventDef : provider.getEventDefinitions()) {
							UUID uuid = eventDef.getUUID();
							if (uuid == null) {
								LOG.error("Provider, " + provider.getName() + " (" + provider.getUuid() + ") has an Event Definition with null UUID.");
							} else {
								eventDefinitions.put(uuid, eventDef);
								eventDefinitionToProvider.put(uuid, provider.getUuid());
								addAttributeDefinitions(eventDef.getAttributeDefinitions());
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
								addAttributeDefinitions(stateDef.getAttributeDefinitions());
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
								addAttributeDefinitions(actionDef.getAttributeDefinitions());
							}
						}
					}
				}// If configuration good
		} catch (Exception e) {
			LOG.error("Failed to initialize provider, " + provider.getName() + " (" + provider.getUuid() + "):" + e.getMessage(), e);
    		errors.add(e);
    	}
    	
    	return errors;
    }
    
    private void addAttributeDefinitions(List<AttributeDefinition<?, ?>> attributeDefinitions) {
    	for (AttributeDefinition<?, ?> attributeDefinition : attributeDefinitions) {
    		this.attributeDefinitions.put(attributeDefinition.getUUID(), attributeDefinition);
    	}
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
	public void run(Action action) throws ActionFailureException, ActionAttributeException {
		if (LOG.isTraceEnabled()) {
			LOG.entry(action);
		}
		
		final UUID actionDefinitionUuid = action.getAppliesToActionDefinition();
		final UUID providerUuid = actionDefinitionToProvider.get(actionDefinitionUuid);
		
		final Provider provider = providers.get(providerUuid);
		final ActionDefinition actionDefinition = actionDefinitions.get(actionDefinitionUuid);
		
		if (provider == null) {
			throw new ActionFailureException("Could not find Provider associated with this Action.");
		}
		
		if (actionDefinition == null) {
			throw new ActionFailureException("Could not find Action Definition associated with this Action");
		}
		
		try {
			actionDefinition.run(action, provider);
		} catch (ActionFailureException | ActionAttributeException e) {
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
	public ActionDefinition getActionDefinition(UUID actionDefinitionUuid) {
		return actionDefinitions.get(actionDefinitionUuid);
	}

	@Override
	public EventDefinition getEventDefinition(UUID eventDefinitionUuid) {
		return eventDefinitions.get(eventDefinitionUuid);
	}

	@Override
	public StateDefinition getStateDefinition(UUID stateDefinitionUuid) {
		return stateDefinitions.get(stateDefinitionUuid);
	}

	@Override
	public AttributeDefinition<?, ?> getAttributeDefinition(UUID attributeDefinitionUuid) {
		return attributeDefinitions.get(attributeDefinitionUuid);
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

	@Override
	public AttributeCollection getProviderSettings(UUID providerUuid) {
		return providerToSettings.get(providerUuid);
	}

	@Override
	public void setProviderSettings(UUID providerUuid, AttributeCollection settings) {
		providerToSettings.put(providerUuid, settings);
	}

}
