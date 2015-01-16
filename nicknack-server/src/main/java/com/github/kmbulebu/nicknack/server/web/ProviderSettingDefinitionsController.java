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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.kmbulebu.nicknack.core.providers.settings.SettingDefinition;
import com.github.kmbulebu.nicknack.server.Application;
import com.github.kmbulebu.nicknack.server.model.ProviderSettingDefinitionsResource;
import com.github.kmbulebu.nicknack.server.services.ProviderSettingDefinitionsService;
import com.github.kmbulebu.nicknack.server.services.exceptions.ProviderNotFoundException;

@RestController
@RequestMapping(value="/api/providers/{providerUuid}/settingDefinitions", produces={"application/hal+json"})
@ExposesResourceFor(ProviderSettingDefinitionsResource.class)
public class ProviderSettingDefinitionsController {
	
	private static final Logger LOG = LogManager.getLogger(Application.APP_LOGGER_NAME);
	
	@Autowired
	private RelProvider relProvider;
	
	@Autowired
	private EntityLinks entityLinks;
	
	@Autowired
	private ProviderSettingDefinitionsService settingDefinitionsService;
	
	@RequestMapping(value="", method={RequestMethod.GET, RequestMethod.HEAD})
	public ProviderSettingDefinitionsResource getProviderSettingDefinitions(@PathVariable UUID providerUuid) throws ProviderNotFoundException {
		if (LOG.isTraceEnabled()) {
			LOG.entry(providerUuid);
		}
		
		final List<? extends SettingDefinition<?, ?>> settingDefinitions = settingDefinitionsService.getSettingDefinitions(providerUuid);
		
		final ProviderSettingDefinitionsResource settingDefinitionsResource = new ProviderSettingDefinitionsResource();
		settingDefinitionsResource.setSettingDefinitions(settingDefinitions);
		
		settingDefinitionsResource.add(linkTo(methodOn(ProviderSettingDefinitionsController.class).getProviderSettingDefinitions(providerUuid)).withSelfRel());
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(settingDefinitionsResource);
		}
		return settingDefinitionsResource;
	}

}
