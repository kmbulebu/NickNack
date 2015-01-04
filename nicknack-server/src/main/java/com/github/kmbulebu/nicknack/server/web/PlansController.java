package com.github.kmbulebu.nicknack.server.web;

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

import com.github.kmbulebu.nicknack.server.Application;
import com.github.kmbulebu.nicknack.server.model.ActionResource;
import com.github.kmbulebu.nicknack.server.model.PlanResource;
import com.github.kmbulebu.nicknack.server.services.PlansService;
import com.github.kmbulebu.nicknack.server.services.exceptions.ActionNotFoundException;
import com.github.kmbulebu.nicknack.server.services.exceptions.EventFilterNotFoundException;
import com.github.kmbulebu.nicknack.server.services.exceptions.PlanNotFoundException;
import com.github.kmbulebu.nicknack.server.services.exceptions.StateFilterNotFoundException;

@RestController
@RequestMapping(value="/api/plans", produces={"application/hal+json"})
@ExposesResourceFor(PlanResource.class)
public class PlansController {
	
	private static final Logger LOG = LogManager.getLogger(Application.APP_LOGGER_NAME);
	
	@Autowired
	private PlansService plansService;
	
	@Autowired
	private RelProvider relProvider;
	
	@Autowired
	private EntityLinks entityLinks;
	
	@RequestMapping(value="", method={RequestMethod.GET, RequestMethod.HEAD})
	public Resources<PlanResource> getPlans() throws PlanNotFoundException, ActionNotFoundException, EventFilterNotFoundException, StateFilterNotFoundException {
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
	public PlanResource createPlan(@RequestBody PlanResource newPlan) throws PlanNotFoundException, ActionNotFoundException, EventFilterNotFoundException, StateFilterNotFoundException {
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
	
	@RequestMapping(value="/{uuid}", method={RequestMethod.PUT}, consumes={MediaType.APPLICATION_JSON_VALUE})
	@ResponseStatus(HttpStatus.OK)
	public PlanResource modifyPlan(@PathVariable UUID uuid, @RequestBody PlanResource plan) throws PlanNotFoundException, ActionNotFoundException, EventFilterNotFoundException, StateFilterNotFoundException {
		if (LOG.isTraceEnabled()) {
			LOG.entry(plan);
		}
		
		if (!uuid.equals(plan.getUUID())) {
			throw new IllegalArgumentException("Plan uuid did not match path.");
		}
		
		final PlanResource resource = plansService.modifyPlan(plan);
		
		addLinks(resource);
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(resource);
		}
		return resource;
	}
	
	@RequestMapping(value="/{uuid}", method={RequestMethod.GET, RequestMethod.HEAD})
	public PlanResource getPlan(@PathVariable UUID uuid) throws PlanNotFoundException, ActionNotFoundException, EventFilterNotFoundException, StateFilterNotFoundException {
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
	public void deletePlan(@PathVariable UUID uuid) throws PlanNotFoundException {
		if (LOG.isTraceEnabled()) {
			LOG.entry(uuid);
		}
		
		plansService.deletePlan(uuid);
		
		if (LOG.isTraceEnabled()) {
			LOG.exit();
		}
	}
	
	private void addLinks(PlanResource resource) throws PlanNotFoundException, ActionNotFoundException, EventFilterNotFoundException, StateFilterNotFoundException {
		resource.add(linkTo(methodOn(PlansController.class).getPlan(resource.getUUID())).withSelfRel());
		resource.add(linkTo(methodOn(PlansEventFiltersController.class).getEventFilters(resource.getUUID())).withRel("eventFilters"));
		resource.add(linkTo(methodOn(PlansStateFiltersController.class).getStateFilters(resource.getUUID())).withRel("stateFilters"));
		resource.add(linkTo(methodOn(ActionsController.class).getActionsByPlan(resource.getUUID())).withRel(relProvider.getCollectionResourceRelFor(ActionResource.class)));
	}
	
	private void addLinks(Resources<PlanResource> resources) throws PlanNotFoundException, ActionNotFoundException, EventFilterNotFoundException, StateFilterNotFoundException {
		resources.add(entityLinks.linkToCollectionResource(PlanResource.class));
		
		for (PlanResource resource : resources.getContent()) {
			addLinks(resource);
		}
	}

}
