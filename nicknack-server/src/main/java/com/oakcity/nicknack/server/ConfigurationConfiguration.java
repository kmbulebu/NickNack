package com.oakcity.nicknack.server;

import java.nio.file.Path;
import java.nio.file.Paths;

import javax.annotation.PreDestroy;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.oakcity.nicknack.core.providers.ProviderService;
import com.oakcity.nicknack.core.providers.ProviderServiceImpl;


@Configuration
public class ConfigurationConfiguration {
	
	@Value(value = "${nicknack.user.config:./config/nicknack_config.xml}")
	private String configFilePathProperty;

	@Bean
	public XMLConfiguration configuration() throws ConfigurationException {
		// For now, a hard coded file.
		final Path configFilePath = Paths.get(configFilePathProperty);
		final XMLConfiguration configuration = new XMLConfiguration(configFilePath.toFile());
		return configuration;
	}

	@Bean
	public ProviderService providerService() throws ConfigurationException {
		XMLConfiguration configuration = configuration();
		final ProviderService providerService = ProviderServiceImpl.getInstance(configuration);
		configuration.save();
		return providerService;
	}
	
	
	@PreDestroy
	public void saveConfiguration() throws ConfigurationException {
		configuration().save();
	}

}
