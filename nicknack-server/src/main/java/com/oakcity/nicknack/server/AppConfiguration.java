package com.oakcity.nicknack.server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@ComponentScan(basePackages={"com.oakcity.nicknack.server","com.mangofactory.swagger.controllers","com.mangofactory.swagger.configuration"})
//@EnableAutoConfiguration
@Configuration
public class AppConfiguration {
	
	public static final String APP_LOGGER_NAME = "nicknack-server";
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer properties() {
	    PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
	    return propertySourcesPlaceholderConfigurer;
	}

}
