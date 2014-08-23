package com.oakcity.nicknack.server;

import java.io.IOException;
import java.nio.file.Files;
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
	
	@Value(value = "${nicknack.user.config:./conf/nicknack_config.xml}")
	private String configFilePathProperty;
	
	@Value(value = "${nicknack.providers.path:./providers}")
	private String providersPathProperty;

	@Bean
	public XMLConfiguration configuration() throws ConfigurationException {
		// For now, a hard coded file.
		final Path configFilePath = Paths.get(configFilePathProperty);
		final XMLConfiguration configuration = new XMLConfiguration(configFilePath.toFile());
		return configuration;
	}

	@Bean
	public ProviderService providerService() throws ConfigurationException, IOException {
		final XMLConfiguration configuration = configuration();
		final Path providersPath = Paths.get(providersPathProperty);
		if (Files.notExists(providersPath)) {
			Files.createDirectory(providersPath);
		}
		final ProviderService providerService = ProviderServiceImpl.getInstance(providersPath, configuration);
		configuration.save();
		return providerService;
	}
	
	
	@PreDestroy
	public void saveConfiguration() throws ConfigurationException {
		configuration().save();
	}

}
