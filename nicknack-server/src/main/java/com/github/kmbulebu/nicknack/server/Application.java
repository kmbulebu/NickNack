package com.github.kmbulebu.nicknack.server;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.annotation.EnableAsync;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.github.kmbulebu.nicknack.core.providers.ProviderService;
import com.github.kmbulebu.nicknack.core.providers.ProviderServiceImpl;

@Configuration
@EnableAutoConfiguration
@EnableSpringDataWebSupport
@EnableAsync
@ComponentScan
@PropertySource(value = { "file:${nicknack.configfile}" }, ignoreResourceNotFound = true)
public class Application {

	public static final String APP_LOGGER_NAME = "nicknack-server";

	private static final Logger LOG = LogManager.getLogger(APP_LOGGER_NAME);

	@Value(value = "${nicknack.providers.path:./providers}")
	private String providersPathProperty;

	/*
	 * @Bean public ServletRegistrationBean
	 * servletRegistrationBean(EventStreamingServlet eventStreamingServlet){
	 * return new ServletRegistrationBean(eventStreamingServlet,
	 * "/api/eventsStream"); }
	 * 
	 * @Bean public EventStreamingServlet eventStreamingServlet() { return new
	 * EventStreamingServlet(); }*
	 */

	@Bean
	public static PropertySourcesPlaceholderConfigurer properties() {
		PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
		return propertySourcesPlaceholderConfigurer;
	}

	@Bean
	public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
		final MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
		mappingJackson2HttpMessageConverter.setPrettyPrint(true);
		mappingJackson2HttpMessageConverter.getObjectMapper().setSerializationInclusion(Include.ALWAYS);
		return mappingJackson2HttpMessageConverter;
	}

	@Bean
	public Path providersPath() throws IOException {
		final Path providersPath = Paths.get(providersPathProperty);
		if (Files.notExists(providersPath)) {
			Files.createDirectory(providersPath);
		}
		return providersPath;
	}

	@Bean
	public ProviderService providerService() throws IOException {
		final Path providersPath = Paths.get(providersPathProperty);
		if (Files.notExists(providersPath)) {
			Files.createDirectory(providersPath);
		}
		final ProviderService providerService = ProviderServiceImpl.getInstance(providersPath);

		// TODO Load settings from DB and set them, then init.
		// providerService.setProviderSettings(providerUuid, settings);
		// providerService.initialize();
		return providerService;
	}

	@Value(value = "${nicknack.db.path:./db}")
	private String dbPathProperty;

	@Bean(destroyMethod = "shutdown")
	public GraphDatabaseService graphDatabaseService() {
		return new GraphDatabaseFactory().newEmbeddedDatabase(dbPathProperty);
	}

	public static void main(String[] args) {
		LOG.info("Starting NickNack server.");
		SpringApplication.run(Application.class, args);
	}

}
