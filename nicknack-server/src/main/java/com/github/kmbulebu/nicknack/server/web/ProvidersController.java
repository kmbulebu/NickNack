package com.github.kmbulebu.nicknack.server.web;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.RelProvider;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.kmbulebu.nicknack.core.providers.Provider;
import com.github.kmbulebu.nicknack.core.providers.ProviderService;
import com.github.kmbulebu.nicknack.server.Application;
import com.github.kmbulebu.nicknack.server.model.ActionDefinitionResource;
import com.github.kmbulebu.nicknack.server.model.EventDefinitionResource;
import com.github.kmbulebu.nicknack.server.model.ProviderResource;
import com.github.kmbulebu.nicknack.server.model.StateDefinitionResource;
import com.github.kmbulebu.nicknack.server.model.StatesResource;


@RestController
@RequestMapping(value="/api/providers", produces={"application/hal+json"})
@ExposesResourceFor(ProviderResource.class)
public class ProvidersController {
	
	private static final Logger LOG = LogManager.getLogger(Application.APP_LOGGER_NAME);
	
	@Autowired
	private ProviderService providerService;
	
	@Autowired
	private RelProvider relProvider;
	
	@Autowired
	private EntityLinks entityLinks;
		
	@RequestMapping(value="", method={RequestMethod.GET, RequestMethod.HEAD})
	public Resources<ProviderResource> getProviders() {
		if (LOG.isTraceEnabled()) {
			LOG.entry();
		}
		
		final Collection<Provider> providers = providerService.getProviders().values();
		
		final List<ProviderResource> providerResources = new ArrayList<ProviderResource>(providers.size());
		
		for (Provider provider : providers) {
			final ProviderResource resource = new ProviderResource(provider);
			resource.add(linkTo(methodOn(ProvidersController.class).getProvider(provider.getUuid())).withSelfRel());
			resource.add(linkTo(methodOn(EventDefinitionsController.class).getEventDefinitions(provider.getUuid())).withRel(relProvider.getCollectionResourceRelFor(EventDefinitionResource.class)));
			resource.add(linkTo(methodOn(ActionDefinitionsController.class).getActionDefinitions(provider.getUuid())).withRel(relProvider.getCollectionResourceRelFor(ActionDefinitionResource.class)));
			resource.add(linkTo(methodOn(StateDefinitionsController.class).getStateDefinitions(provider.getUuid())).withRel(relProvider.getCollectionResourceRelFor(StateDefinitionResource.class)));
			resource.add(linkTo(methodOn(StatesController.class).getAllStates(provider.getUuid())).withRel(relProvider.getCollectionResourceRelFor(StatesResource.class)));
			providerResources.add(resource);
			
		}
		
		final Resources<ProviderResource> resources = new Resources<ProviderResource>(providerResources);
		resources.add(entityLinks.linkToCollectionResource(ProviderResource.class));
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(resources);	
		}
		return resources;
	}
	
	@RequestMapping(value="/{uuid}", method={RequestMethod.GET, RequestMethod.HEAD})
	public ProviderResource getProvider(@PathVariable UUID uuid) {
		if (LOG.isTraceEnabled()) {
			LOG.entry(uuid);
		}
		
		final Provider provider = providerService.getProviders().get(uuid); 
		final ProviderResource resource = new ProviderResource(provider);
		resource.add(linkTo(methodOn(ProvidersController.class).getProvider(provider.getUuid())).withSelfRel());
		resource.add(linkTo(methodOn(EventDefinitionsController.class).getEventDefinitions(provider.getUuid())).withRel(relProvider.getCollectionResourceRelFor(EventDefinitionResource.class)));
		resource.add(linkTo(methodOn(ActionDefinitionsController.class).getActionDefinitions(provider.getUuid())).withRel(relProvider.getCollectionResourceRelFor(ActionDefinitionResource.class)));
		resource.add(linkTo(methodOn(StateDefinitionsController.class).getStateDefinitions(provider.getUuid())).withRel(relProvider.getCollectionResourceRelFor(StateDefinitionResource.class)));
		resource.add(linkTo(methodOn(StatesController.class).getAllStates(provider.getUuid())).withRel(relProvider.getCollectionResourceRelFor(StatesResource.class)));
		
			
		if (LOG.isTraceEnabled()) {
			LOG.exit(resource);
		}
		return resource;
	}
	
	

}
