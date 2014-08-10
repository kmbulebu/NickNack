package com.oakcity.nicknack.server.web;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.RelProvider;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.oakcity.nicknack.server.AppConfiguration;
import com.oakcity.nicknack.server.model.AttributeFilterResource;
import com.oakcity.nicknack.server.services.AttributeFiltersService;

@RestController
@RequestMapping(value="/api/plans/{planUuid}/eventFilters/{eventFilterUuid}/attributeFilters", produces={"application/hal+json"})
@ExposesResourceFor(AttributeFilterResource.class)
public class AttributeFiltersController {
	
	private static final Logger LOG = LogManager.getLogger(AppConfiguration.APP_LOGGER_NAME);
	
	@Autowired
	private AttributeFiltersService attributeFiltersService;
	
	@Autowired
	private RelProvider relProvider;
	
	@Autowired
	private EntityLinks entityLinks;
	
	@RequestMapping(value="", method={RequestMethod.GET, RequestMethod.HEAD})
	public Resources<AttributeFilterResource> getAttributeFilters(@PathVariable UUID planUuid, @PathVariable UUID eventFilterUuid) {
		if (LOG.isTraceEnabled()) {
			LOG.entry();
		}
		
		final List<AttributeFilterResource> attributeFilterResources = attributeFiltersService.getAttributeFilters(eventFilterUuid);
				
		final Resources<AttributeFilterResource> resources = new Resources<AttributeFilterResource>(attributeFilterResources);
		
		// Add links
		addLinks(planUuid, eventFilterUuid, resources);
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(resources);
		}
		return resources;
	}
	
	@RequestMapping(value="", method={RequestMethod.POST}, consumes={MediaType.APPLICATION_JSON_VALUE})
	@ResponseStatus(HttpStatus.CREATED)
	public AttributeFilterResource createAttributeFilter(@PathVariable UUID planUuid, @PathVariable UUID eventFilterUuid, @RequestBody AttributeFilterResource newAttributeFilter) {
		if (LOG.isTraceEnabled()) {
			LOG.entry(newAttributeFilter);
		}
		
		final AttributeFilterResource resource = attributeFiltersService.createAttributeFilter(eventFilterUuid, newAttributeFilter);
		
		addLinks(planUuid, eventFilterUuid, resource);
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(resource);
		}
		return resource;
	}
	
	@RequestMapping(value="/{uuid}", method={RequestMethod.GET, RequestMethod.HEAD})
	public AttributeFilterResource getAttributeFilter(@PathVariable UUID planUuid, @PathVariable UUID eventFilterUuid, @PathVariable UUID uuid) {
		if (LOG.isTraceEnabled()) {
			LOG.entry(uuid);
		}
		
		final AttributeFilterResource resource = attributeFiltersService.getAttributeFilter(uuid);
		
		addLinks(planUuid, eventFilterUuid, resource);
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(resource);
		}
		return resource;
	}
	
	@RequestMapping(value="/{uuid}", method={RequestMethod.DELETE})
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteAttributeFilter(@PathVariable UUID planUuid, @PathVariable UUID eventFilterUuid, @PathVariable UUID uuid) {
		if (LOG.isTraceEnabled()) {
			LOG.entry(uuid);
		}
		
		attributeFiltersService.deleteAttributeFilter(uuid);
		
		if (LOG.isTraceEnabled()) {
			LOG.exit();
		}
	}
	
	private void addLinks(UUID planUuid, UUID eventFilterUuid, AttributeFilterResource resource) {
		resource.add(linkTo(methodOn(AttributeFiltersController.class).getAttributeFilter(planUuid, eventFilterUuid, resource.getUuid())).withSelfRel());
	}
	
	private void addLinks(UUID planUuid, UUID eventFilterUuid, Resources<AttributeFilterResource> resources) {
		resources.add(linkTo(methodOn(AttributeFiltersController.class).getAttributeFilters(planUuid, eventFilterUuid)).withSelfRel());
		
		for (AttributeFilterResource resource : resources.getContent()) {
			addLinks(planUuid, eventFilterUuid, resource);
		}
	}

}
