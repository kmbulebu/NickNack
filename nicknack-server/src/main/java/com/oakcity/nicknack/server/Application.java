package com.oakcity.nicknack.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
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
public class Application {
	
	@Bean
	public ServletRegistrationBean servletRegistrationBean(EventStreamingServlet eventStreamingServlet){
	    return new ServletRegistrationBean(eventStreamingServlet, "/api/eventsStream");
	}
	
	@Bean
	public EventStreamingServlet eventStreamingServlet() {
		return new EventStreamingServlet();
	}
	
	
	public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
	

}
