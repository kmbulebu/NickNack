package com.github.kmbulebu.nicknack.server.controllers;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.HEAD;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.github.kmbulebu.nicknack.core.attributes.AttributeValueParser.InvalidValueException;
import com.github.kmbulebu.nicknack.core.valuetypes.ValueType.ParseException;
import com.github.kmbulebu.nicknack.server.Application;
import com.github.kmbulebu.nicknack.server.exceptions.EntityDoesNotExist;
import com.github.kmbulebu.nicknack.server.restmodel.Provider;
import com.github.kmbulebu.nicknack.server.restmodel.View;
import com.github.kmbulebu.nicknack.server.services.ProvidersService;

@RestController
@RequestMapping(value="/api/providers")
public class ProvidersController {
	
	private static final Logger LOG = LogManager.getLogger(Application.APP_LOGGER_NAME);
	
	@Inject
	private ProvidersService providersService;
	
	@JsonView(View.Summary.class)
	@RequestMapping(method={GET, HEAD})
	public List<Provider> getAllProviders() {
		if (LOG.isTraceEnabled()) {
			LOG.entry();
		}
		
		final List<Provider> providers = providersService.getAllProviders();
		
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(providers);
		}
		return providers;
	}
	
	@RequestMapping(method={GET, HEAD}, value="/{uuid}")
	public Provider getProvider(@PathVariable UUID uuid) {
		if (LOG.isTraceEnabled()) {
			LOG.entry();
		}
		
		final Provider provider = providersService.getProvider(uuid); 
		
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(provider);
		}
		return provider;
	}
	

	@RequestMapping(method={PUT}, value="/{uuid}")
	public void updateProviderSettings(@PathVariable UUID uuid, @RequestBody Provider provider) throws ParseException, InvalidValueException, EntityDoesNotExist {
		if (LOG.isTraceEnabled()) {
			LOG.entry(provider);
		}
		
		if (!provider.getUuid().equals(uuid)) {
			// TODO Throw exception to generate 400.
		}
		
		providersService.updateSettings(provider); 
		
		
		if (LOG.isTraceEnabled()) {
			LOG.exit();
		}
	}

}
