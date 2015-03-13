package com.github.kmbulebu.nicknack.server.web;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.RelProvider;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.kmbulebu.nicknack.server.Application;
import com.github.kmbulebu.nicknack.server.model.ProviderSettingsResource;
import com.github.kmbulebu.nicknack.server.services.ProviderSettingsService;
import com.github.kmbulebu.nicknack.server.services.exceptions.ProviderNotFoundException;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

@RestController
@RequestMapping(value="/api/providers/{providerUuid}/settings", produces={"application/hal+json"})
@ExposesResourceFor(ProviderSettingsResource.class)
public class ProviderSettingsController {
	
	private static final Logger LOG = LogManager.getLogger(Application.APP_LOGGER_NAME);
	
	@Autowired
	private RelProvider relProvider;
	
	@Autowired
	private EntityLinks entityLinks;
	
	@Autowired
	private ProviderSettingsService settingsService;
	
	@RequestMapping(value="", method={RequestMethod.GET, RequestMethod.HEAD})
	public ProviderSettingsResource getProviderSettings(@PathVariable UUID providerUuid) throws ProviderNotFoundException {
		if (LOG.isTraceEnabled()) {
			LOG.entry(providerUuid);
		}
		
		final Map<String, List<String>> settings = settingsService.getProviderSettings(providerUuid);
		
		final ProviderSettingsResource settingsResource = new ProviderSettingsResource();
		settingsResource.setSettings(settings);
		settingsResource.setComplete(settingsService.isProviderSettingsComplete(providerUuid));
		settingsResource.setErrors(settingsService.getProviderSettingErrors(providerUuid));
		settingsResource.setEnabled(settingsService.isProviderEnabled(providerUuid));
		settingsResource.add(linkTo(methodOn(ProviderSettingsController.class).getProviderSettings(providerUuid)).withSelfRel());
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(settingsResource);
		}
		return settingsResource;
	}
	
	@RequestMapping(value="", method={RequestMethod.POST})
	public void setProviderSettings(@PathVariable UUID providerUuid, @RequestBody ProviderSettingsResource updatedSettings) throws ProviderNotFoundException {
		if (LOG.isTraceEnabled()) {
			LOG.entry(providerUuid, updatedSettings);
		}
		
		settingsService.setProviderSettings(providerUuid, updatedSettings.getSettings(), !updatedSettings.isEnabled());
		
		if (LOG.isTraceEnabled()) {
			LOG.exit();
		}
	}

	
	/**
	 * Updates one setting
	 */
//	@RequestMapping(value="{{key}}", method={RequestMethod.POST}, consumes={MediaType.APPLICATION_JSON_VALUE})
//	@ResponseStatus(HttpStatus.CREATED)
}
