package com.oakcity.nicknack.server;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan
@EnableAutoConfiguration
@Configuration
public class AppConfiguration {
	
	public static final String APP_LOGGER_NAME = "nicknack-server";

}
