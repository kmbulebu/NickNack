package com.oakcity.nicknack.server;

import java.nio.file.Path;
import java.nio.file.Paths;

import javax.annotation.PreDestroy;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.hateoas.config.EnableEntityLinks;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.oakcity.nicknack.core.providers.ProviderService;
import com.oakcity.nicknack.core.providers.ProviderServiceImpl;

@EnableWebMvc
@EnableEntityLinks
@EnableHypermediaSupport(type =  HypermediaType.HAL )
@EnableJpaRepositories
@EnableTransactionManagement
@EnableSpringDataWebSupport
@Configuration
public class WebConfiguration extends WebMvcConfigurerAdapter {

	protected static final String PROPERTY_NAME_HIBERNATE_DIALECT = "hibernate.dialect";
	protected static final String PROPERTY_NAME_HIBERNATE_FORMAT_SQL = "hibernate.format_sql";
	protected static final String PROPERTY_NAME_HIBERNATE_NAMING_STRATEGY = "hibernate.ejb.naming_strategy";
	protected static final String PROPERTY_NAME_HIBERNATE_SHOW_SQL = "hibernate.show_sql";
	protected static final String PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO = "hibernate.hbm2ddl.auto";

	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		configurer.defaultContentType(MediaType.APPLICATION_JSON);
	}
	
	@Bean
	public XMLConfiguration configuration() throws ConfigurationException {
		// For now, a hard coded file.
		final Path configFilePath = Paths.get("nicknack_config.xml");
		System.out.println(configFilePath.toAbsolutePath().toString());
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
