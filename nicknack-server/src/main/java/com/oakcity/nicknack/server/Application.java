package com.oakcity.nicknack.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.hateoas.config.EnableEntityLinks;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.oakcity.nicknack.server.web.EventStreamingServlet;

@Configuration
@EnableAutoConfiguration
@EnableEntityLinks
@EnableHypermediaSupport(type =  HypermediaType.HAL )
@EnableJpaRepositories
@EnableTransactionManagement
@EnableSpringDataWebSupport
@ComponentScan
@PropertySource(value = { "file:${nicknack.configfile}" }, ignoreResourceNotFound=true)
public class Application {
	
	public static final String APP_LOGGER_NAME = "nicknack-server";
	
	private static final Logger LOG = LogManager.getLogger(APP_LOGGER_NAME);


	@Bean
	public ServletRegistrationBean servletRegistrationBean(EventStreamingServlet eventStreamingServlet){
	    return new ServletRegistrationBean(eventStreamingServlet, "/api/eventsStream");
	}
	
	@Bean
	public EventStreamingServlet eventStreamingServlet() {
		return new EventStreamingServlet();
	}
	
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer properties() {
	    PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
	    return propertySourcesPlaceholderConfigurer;
	}
	

	public static void main(String[] args) {
		LOG.info("Starting NickNack server.");
        SpringApplication.run(Application.class, args);
    }
	

}
