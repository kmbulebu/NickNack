package com.github.kmbulebu.nicknack.core.providers;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
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
import com.github.kmbulebu.nicknack.core.providers.settings.SettingDefinition;
import com.github.kmbulebu.nicknack.core.states.StateDefinition;

public class ProviderServiceImpl implements ProviderService, OnEventListener, rx.Observable.OnSubscribe<Event> {
	
	private static final Logger LOG = LogManager.getLogger();
	
	private static ProviderServiceImpl service;
    private ProviderLoader loader;
    
    // TODO Consider a separate ProviderRegistry class to maintain these relationships.
    private final Map<UUID, EventDefinition> eventDefinitions = new HashMap<UUID, EventDefinition>();
    private final Map<UUID, StateDefinition> stateDefinitions = new HashMap<UUID, StateDefinition>();
    private final Map<UUID, ActionDefinition> actionDefinitions = new HashMap<UUID, ActionDefinition>();
    private final Map<UUID, Provider> providers = new HashMap<UUID, Provider>();
    private final Map<UUID, UUID> eventDefinitionToProvider = new HashMap<UUID, UUID>();
    private final Map<UUID, UUID> stateDefinitionToProvider = new HashMap<UUID, UUID>();
    private final Map<UUID, UUID> actionDefinitionToProvider = new HashMap<UUID, UUID>();
    private final Map<UUID, ProviderConfiguration> providerConfigurations = new HashMap<>();
    
    
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
    
    // TODO This all needs pulled into a separate ProviderConfigurationLoader or whatever class that can properly return the results good or bad with a list of problems.
	@SuppressWarnings("unchecked")
	protected ProviderConfiguration loadProviderConfiguration(boolean disabled, List<? extends SettingDefinition<?,?>> definitionsList, Configuration configuration) {
    	final ProviderConfigurationImpl providerConfiguration = new ProviderConfigurationImpl();
    	providerConfiguration.setComplete(true);
    	providerConfiguration.setEnabled(!disabled);
    	if (definitionsList != null) {
	    	for (SettingDefinition<?,?> definition : definitionsList) {
	    		final boolean exists = configuration.containsKey(definition.getKey());
	    		final boolean required = definition.isRequired(); 
	    		
	    		// Make sure we have all required settings.
	    		if (required && !exists) {
	    			providerConfiguration.addError(definition.getKey(), "Required.");
	    			providerConfiguration.setComplete(false);
	    		}
	    		
	    		@SuppressWarnings({ "rawtypes" })
				final List<String> stringValues = (List) configuration.getList(definition.getKey());
	    		
	    		@SuppressWarnings("rawtypes")
				final List values = definition.getSettingType().load(stringValues);
	    		
	    		providerConfiguration.setValues(definition, values);
	    	}
    	}
    	
    	return providerConfiguration;
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
			
			final ProviderConfiguration providerConfiguration = loadProviderConfiguration(providerConfig.getBoolean("disabled", false), provider.getSettingDefinitions(), providerConfig);
			providerConfigurations.put(provider.getUuid(), providerConfiguration);
			
			initProvider(provider, providerConfiguration);
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

	@Override
	public Map<String, List<?>> getProviderSettings(UUID providerUuid) {
		Provider provider = getProviders().get(providerUuid);
		ProviderConfiguration config = providerConfigurations.get(providerUuid);
		
		if (provider == null || config == null) {
			return null;
		}
		
		final Map<String, List<?>> settings = new HashMap<>();
		if (provider.getSettingDefinitions() != null) {
			for (SettingDefinition<?, ?> definition : provider.getSettingDefinitions()) {
				List<?> values = config.getValues(definition);
				
				if (values == null) {
					settings.put(definition.getKey(), new ArrayList<Object>());
				} else {
					settings.put(definition.getKey(), values);
				}
			}
		}
		return settings;
		
	}
	
	@Override
	public boolean isProviderSettingsComplete(UUID providerUuid) {
		Provider provider = getProviders().get(providerUuid);
		ProviderConfiguration config = providerConfigurations.get(providerUuid);
		
		if (provider == null || config == null) {
			return false;
		}
		
		return config.isComplete();
	}
	
	@Override
	public Map<String, List<String>> getProviderSettingsErrors(UUID providerUuid) {
		Provider provider = getProviders().get(providerUuid);
		ProviderConfiguration config = providerConfigurations.get(providerUuid);
		
		if (provider == null || config == null) {
			return null;
		}
		
		return config.getErrors();
	}
	
	@Override
	public boolean isProviderEnabled(UUID providerUuid) {
		Provider provider = getProviders().get(providerUuid);
		ProviderConfiguration config = providerConfigurations.get(providerUuid);
		
		if (provider == null || config == null) {
			return false;
		}
		
		return config.isEnabled();
	}

	@Override
	public void setProviderSettings(UUID providerUuid, Map<String, List<?>> settings, boolean disabled) {
		final Provider provider = getProviders().get(providerUuid);
		final ProviderConfigurationImpl config = (ProviderConfigurationImpl) providerConfigurations.get(providerUuid);
		config.setAllValues(settings);
		config.setEnabled(!disabled);
		final String configKey = "providers.uuid" + providerUuid.toString().replaceAll("-", "");

		final Configuration providerConfig = configuration.configurationAt(configKey, true);
		providerConfig.setProperty("disabled", disabled);
		
		final List<? extends SettingDefinition<?, ?>> settingDefinitions = provider.getSettingDefinitions();
		
		for (SettingDefinition<?, ?> definition : settingDefinitions) {
			providerConfig.clearProperty(definition.getKey());
			final List<?> values = settings.get(definition.getKey());
			if (values != null) {
				if (definition.isArray()) {
					for (Object value : values) {
						providerConfig.addProperty(definition.getKey(), definition.getSettingType().save(value));
					}
				} else if (!values.isEmpty()){
					providerConfig.setProperty(definition.getKey(), definition.getSettingType().save(values.get(0)));
				}
			}
		}
		
		try {
			configuration.save();
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		try {
			restartProvider(providerUuid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
}

	protected void restartProvider(UUID providerUuid) throws Exception {
		final Provider provider = providers.get(providerUuid);
		
		shutdownProvider(provider);
		initProvider(provider, providerConfigurations.get(providerUuid));
	}
	
	protected void shutdownProvider(Provider provider) throws Exception {
		final Collection<? extends EventDefinition> eventDefs = provider.getEventDefinitions();
		final Collection<? extends StateDefinition> stateDefs = provider.getStateDefinitions();
		final Collection<? extends ActionDefinition> actionDefs = provider.getActionDefinitions();
		
		
		// Ask it to shutdown
		provider.shutdown();
		
		// Remove event definitions.
		if (eventDefs != null) {
			for (EventDefinition eventDef : eventDefs) {
				this.eventDefinitions.remove(eventDef.getUUID());
				this.eventDefinitionToProvider.remove(eventDef.getUUID());
			}
		}
		
		// Remove state definitions.
		if (stateDefs != null) {
			for (StateDefinition stateDef : stateDefs) {
				this.stateDefinitions.remove(stateDef.getUUID());
				this.stateDefinitionToProvider.remove(stateDef.getUUID());
			}
		}
		
		// Remove action definitions
		if (actionDefs != null) {
			for (ActionDefinition actionDef : actionDefs) {
				this.actionDefinitions.remove(actionDef.getUUID());
				this.actionDefinitionToProvider.remove(actionDef.getUUID());
			}
		}
	}
	
	protected void initProvider(Provider provider, ProviderConfiguration providerConfiguration) throws Exception {
		if (!providerConfiguration.isEnabled() || !providerConfiguration.isComplete() ) {
			if (LOG.isInfoEnabled()) {
				LOG.info("Disabling provider: " + provider.getName() + " v" + provider.getVersion() + " by " + provider.getAuthor());
			}
		} else {
			provider.init(providerConfiguration, this);
			
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
		
	}
	
	

}
