package com.oakcity.nicknack.server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.config.EnableEntityLinks;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.oakcity.nicknack.core.providers.ProviderService;
import com.oakcity.nicknack.core.providers.ProviderServiceImpl;

@EnableWebMvc
@EnableEntityLinks
@EnableHypermediaSupport(type = { HypermediaType.HAL })
@Configuration
public class WebConfiguration extends WebMvcConfigurerAdapter {
	
	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		configurer.defaultContentType(MediaType.APPLICATION_JSON);
	}
	
	@Bean
	public ProviderService providerService() {
		return  ProviderServiceImpl.getInstance();
	}

}
