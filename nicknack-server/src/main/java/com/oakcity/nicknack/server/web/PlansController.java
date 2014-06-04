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
import com.oakcity.nicknack.server.model.PlanResource;
import com.oakcity.nicknack.server.services.PlansService;

@RestController
@RequestMapping(value="/plans", produces={"application/hal+json"})
@ExposesResourceFor(PlanResource.class)
public class PlansController {
	
	private static final Logger LOG = LogManager.getLogger(AppConfiguration.APP_LOGGER_NAME);
	
	@Autowired
	private PlansService plansService;
	
	@Autowired
	private RelProvider relProvider;
	
	@Autowired
	private EntityLinks entityLinks;
	
	@RequestMapping(value="", method={RequestMethod.GET, RequestMethod.HEAD})
	public Resources<PlanResource> getPlans() {
		if (LOG.isTraceEnabled()) {
			LOG.entry();
		}
		
		final List<PlanResource> planResources = plansService.getPlans();
				
		final Resources<PlanResource> resources = new Resources<PlanResource>(planResources);
		
		// Add links
		addLinks(resources);
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(resources);
		}
		return resources;
	}
	
	@RequestMapping(value="", method={RequestMethod.POST}, consumes={MediaType.APPLICATION_JSON_VALUE})
	@ResponseStatus(HttpStatus.CREATED)
	public PlanResource createPlan(@RequestBody PlanResource newPlan) {
		if (LOG.isTraceEnabled()) {
			LOG.entry(newPlan);
		}
		
		final PlanResource resource = plansService.createPlan(newPlan);
		
		addLinks(resource);
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(resource);
		}
		return resource;
	}
	
	@RequestMapping(value="/{uuid}", method={RequestMethod.GET, RequestMethod.HEAD})
	public PlanResource getPlan(@PathVariable UUID uuid) {
		if (LOG.isTraceEnabled()) {
			LOG.entry(uuid);
		}
		
		final PlanResource resource = plansService.getPlan(uuid);
		
		addLinks(resource);
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(resource);
		}
		return resource;
	}
	
	@RequestMapping(value="/{uuid}", method={RequestMethod.DELETE})
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletePlan(@PathVariable UUID uuid) {
		if (LOG.isTraceEnabled()) {
			LOG.entry(uuid);
		}
		
		plansService.deletePlan(uuid);
		
		if (LOG.isTraceEnabled()) {
			LOG.exit();
		}
	}
	
	private void addLinks(PlanResource resource) {
		resource.add(linkTo(methodOn(PlansController.class).getPlan(resource.getUUID())).withSelfRel());
		resource.add(linkTo(methodOn(EventFiltersController.class).getEventFilters(resource.getUUID())).withRel("eventFilters"));
		resource.add(linkTo(methodOn(ActionsController.class).getActions(resource.getUUID())).withRel("actions"));
	}
	
	private void addLinks(Resources<PlanResource> resources) {
		resources.add(entityLinks.linkToCollectionResource(PlanResource.class));
		
		for (PlanResource resource : resources.getContent()) {
			addLinks(resource);
		}
	}

}
