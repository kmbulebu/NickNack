package com.oakcity.nicknack.server.web;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RelProvider;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.oakcity.nicknack.server.AppConfiguration;
import com.oakcity.nicknack.server.model.ActionDefinitionResource;
import com.oakcity.nicknack.server.model.EventDefinitionResource;
import com.oakcity.nicknack.server.model.PlanResource;
import com.oakcity.nicknack.server.model.ProviderResource;

@RestController
@RequestMapping(value="/", produces={"application/hal+json"})
public class RootController {
	
	private static final Logger LOG = LogManager.getLogger(AppConfiguration.APP_LOGGER_NAME);
	
	@Autowired
	private RelProvider relProvider;
	
	@Autowired
	private EntityLinks entityLinks;
	
	@RequestMapping(value="", method={RequestMethod.GET, RequestMethod.HEAD})
	public RootResource getRoot() {
		if (LOG.isTraceEnabled()) {
			LOG.entry();
		}
		
		RootResource rootResource = new RootResource();
		
		rootResource.add(linkTo(RootController.class).withSelfRel());
		rootResource.add(getCollectionResourceLink(PlanResource.class));
		rootResource.add(getCollectionResourceLink(ProviderResource.class));
		rootResource.add(getCollectionResourceLink(EventDefinitionResource.class));
		rootResource.add(getCollectionResourceLink(ActionDefinitionResource.class));

		//TODO Add event stream
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(rootResource);
		}
		return rootResource;
	}
	
	private Link getCollectionResourceLink(Class<?> type) {
		return entityLinks.linkToCollectionResource(type).withRel(relProvider.getCollectionResourceRelFor(type));
	}
	
	public static class RootResource extends ResourceSupport {
		
		// TODO Pull from manifest.mf or generate during build.
		private final String version = "0.0.1-SNAPSHOT";
		
		public String getVersion() {
			return version;
		}
	}

}
