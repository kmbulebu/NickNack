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
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.oakcity.nicknack.server.AppConfiguration;
import com.oakcity.nicknack.server.model.ParameterResource;
import com.oakcity.nicknack.server.services.ParametersService;

@RestController
@RequestMapping(value="/plans/{planUuid}/actions/{actionUuid}/parameters", produces={MediaType.APPLICATION_JSON_VALUE})
@ExposesResourceFor(ParameterResource.class)
public class ParametersController {
	
	private static final Logger LOG = LogManager.getLogger(AppConfiguration.APP_LOGGER_NAME);
	
	@Autowired
	private ParametersService parametersService;
	
	@Autowired
	private RelProvider relProvider;
	
	@Autowired
	private EntityLinks entityLinks;
	
	@RequestMapping(value="", method={RequestMethod.GET, RequestMethod.HEAD})
	public Resources<ParameterResource> getParameters(@PathVariable UUID planUuid, @PathVariable UUID actionUuid) {
		if (LOG.isTraceEnabled()) {
			LOG.entry();
		}
		
		final List<ParameterResource> parameterResources = parametersService.getParameters(planUuid);
				
		final Resources<ParameterResource> resources = new Resources<ParameterResource>(parameterResources);
		
		// Add links
		addLinks(planUuid, actionUuid, resources);
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(resources);
		}
		return resources;
	}
	
	@RequestMapping(value="", method={RequestMethod.POST}, consumes={MediaType.APPLICATION_JSON_VALUE})
	public ParameterResource createParameter(@PathVariable UUID planUuid, @PathVariable UUID actionUuid, @RequestBody ParameterResource newParameter) {
		if (LOG.isTraceEnabled()) {
			LOG.entry(newParameter);
		}
		
		final ParameterResource resource = parametersService.createParameter(planUuid, newParameter);
		
		addLinks(planUuid, actionUuid, resource);
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(resource);
		}
		return resource;
	}
	
	@RequestMapping(value="/{uuid}", method={RequestMethod.GET, RequestMethod.HEAD})
	public ParameterResource getParameter(@PathVariable UUID planUuid, @PathVariable UUID actionUuid, @PathVariable UUID uuid) {
		if (LOG.isTraceEnabled()) {
			LOG.entry(uuid);
		}
		
		final ParameterResource resource = parametersService.getParameter(uuid);
		
		addLinks(planUuid, actionUuid, resource);
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(resource);
		}
		return resource;
	}
	
	@RequestMapping(value="/{uuid}", method={RequestMethod.DELETE})
	public void deleteParameter(@PathVariable UUID planUuid, @PathVariable UUID uuid) {
		if (LOG.isTraceEnabled()) {
			LOG.entry(uuid);
		}
		
		parametersService.deleteParameter(uuid);
		
		if (LOG.isTraceEnabled()) {
			LOG.exit();
		}
	}
	
	private void addLinks(UUID planUuid, UUID actionUuid, ParameterResource resource) {
		resource.add(linkTo(methodOn(ParametersController.class).getParameter(planUuid, actionUuid, resource.getUuid())).withSelfRel());
	}
	
	private void addLinks(UUID planUuid, UUID actionUuid, Resources<ParameterResource> resources) {
		resources.add(linkTo(methodOn(ParametersController.class).getParameters(planUuid, actionUuid)).withSelfRel());
		
		for (ParameterResource resource : resources.getContent()) {
			addLinks(planUuid, actionUuid, resource);
		}
	}

}
