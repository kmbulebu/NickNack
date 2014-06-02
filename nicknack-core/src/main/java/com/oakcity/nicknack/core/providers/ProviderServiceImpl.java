package com.oakcity.nicknack.core.providers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.UUID;

import rx.Observable;
import rx.Subscriber;
import rx.observables.ConnectableObservable;

import com.oakcity.nicknack.core.actions.Action;
import com.oakcity.nicknack.core.actions.Action.ActionDefinition;
import com.oakcity.nicknack.core.events.Event;
import com.oakcity.nicknack.core.events.Event.EventDefinition;

public class ProviderServiceImpl implements ProviderService, OnEventListener, rx.Observable.OnSubscribe<Event> {
	
	private static ProviderServiceImpl service;
    private ServiceLoader<Provider> loader;
    
    private final Map<UUID, EventDefinition> eventDefinitions = new HashMap<UUID, EventDefinition>();
    private final Map<UUID, ActionDefinition> actionDefinitions = new HashMap<UUID, ActionDefinition>();
    private final Map<UUID, Provider> providers = new HashMap<UUID, Provider>();
    
    private ConnectableObservable<Event> eventStream;
    private Subscriber<? super Event> subscriber;
    
    private ProviderServiceImpl() {
        loader = ServiceLoader.load(Provider.class);
        eventStream = Observable.create(this).publish();
        eventStream.connect();
        // TODO Do something smart with the errors.
        initializeProviders();
    }

    public static synchronized ProviderServiceImpl getInstance() {
        if (service == null) {
            service = new ProviderServiceImpl();
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
    public Map<UUID, Provider> getProviders() {
    	return Collections.unmodifiableMap(providers);
    }
    
    protected List<Exception> initializeProviders() {
    	Iterator<Provider> providers = loader.iterator();
    	
    	Provider provider = null;
    	List<Exception> errors = new ArrayList<Exception>();
		while (provider == null && providers.hasNext()) {
			try {
				provider = providers.next();
				this.providers.put(provider.getUuid(), provider);
				provider.init(this);
				
				if (provider.getEventDefinitions() != null) {
					for (EventDefinition eventDef : provider.getEventDefinitions()) {
						UUID uuid = eventDef.getUUID();
						if (uuid == null) {
							// Log error.
						} else {
							eventDefinitions.put(uuid, eventDef);
						}
					}
				}
				
				if (provider.getActionDefinitions() != null) {
					for (ActionDefinition actionDef : provider.getActionDefinitions()) {
						UUID uuid = actionDef.getUUID();
						if (uuid == null) {
							// Log error.
						} else {
							actionDefinitions.put(uuid, actionDef);
						}
					}
				}
			} catch (Exception e) {
	    		errors.add(e);
	    	}
		}
		
		return errors;
    	
    }

	@Override
	public void onEvent(Event event) {
		if (subscriber != null) {
			subscriber.onNext(event);
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
	public void run(Action action) {
		final UUID actionDefinitionUuid = action.getAppliesToActionDefinition();
		final ActionDefinition actionDefinition = actionDefinitions.get(actionDefinitionUuid);
		
		try {
			// TODO Run action on executor
			actionDefinition.run(action);
		} catch (Exception e) {
			// TODO Log.
			e.printStackTrace();
		}
	}

}
