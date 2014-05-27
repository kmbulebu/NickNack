package com.oakcity.nicknack.server;

import static com.mangofactory.swagger.models.alternates.Alternates.newRule;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

import com.fasterxml.classmate.TypeResolver;
import com.mangofactory.swagger.configuration.JacksonScalaSupport;
import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.configuration.SwaggerGlobalSettings;
import com.mangofactory.swagger.core.DefaultSwaggerPathProvider;
import com.mangofactory.swagger.core.SwaggerApiResourceListing;
import com.mangofactory.swagger.core.SwaggerPathProvider;
import com.mangofactory.swagger.models.alternates.AlternateTypeProvider;
import com.mangofactory.swagger.models.alternates.WildcardType;
import com.mangofactory.swagger.scanners.ApiListingReferenceScanner;
import com.wordnik.swagger.model.ApiInfo;

@Configuration
public class DocumentationConfiguration {

	public static final String RELATIVE_GROUP = "";

	@Autowired
	private SpringSwaggerConfig springSwaggerConfig;

	@Autowired
	private SwaggerPathProvider relativeSwaggerPathProvider;

	@Bean
	public JacksonScalaSupport jacksonScalaSupport() {
		JacksonScalaSupport jacksonScalaSupport = new JacksonScalaSupport();
		// Set to false to disable
		jacksonScalaSupport.setRegisterScalaModule(true);
		return jacksonScalaSupport;
	}

	/**
	 * Global swagger settings
	 */
	@Bean
	public SwaggerGlobalSettings swaggerGlobalSettings() {
		SwaggerGlobalSettings swaggerGlobalSettings = new SwaggerGlobalSettings();
		swaggerGlobalSettings.setGlobalResponseMessages(springSwaggerConfig
				.defaultResponseMessages());
		@SuppressWarnings("rawtypes")
		final Set<Class> ignorableParameterTypes = new HashSet<Class>();
		ignorableParameterTypes.addAll(springSwaggerConfig
				.defaultIgnorableParameterTypes());
		ignorableParameterTypes.add(Principal.class);

		swaggerGlobalSettings
				.setIgnorableParameterTypes(ignorableParameterTypes);
		AlternateTypeProvider alternateTypeProvider = springSwaggerConfig
				.defaultAlternateTypeProvider();
		TypeResolver typeResolver = new TypeResolver();
		alternateTypeProvider.addRule(newRule(
				typeResolver.resolve(ResponseEntity.class),
				typeResolver.resolve(Void.class)));
		alternateTypeProvider.addRule(newRule(
				typeResolver.resolve(ResponseEntity.class, WildcardType.class),
				typeResolver.resolve(WildcardType.class)));
		alternateTypeProvider.addRule(newRule(
				typeResolver.resolve(HttpEntity.class, WildcardType.class),
				typeResolver.resolve(WildcardType.class)));
		swaggerGlobalSettings.setAlternateTypeProvider(alternateTypeProvider);
		return swaggerGlobalSettings;
	}

	/**
	 * API Info as it appears on the swagger-ui page
	 */
	private ApiInfo apiInfo() {
		ApiInfo apiInfo = new ApiInfo("nicknack", "", "", "", "",
				"");
		return apiInfo;
	}

	/**
	 * Configure a SwaggerApiResourceListing for each swagger instance within
	 * your app. e.g. 1. private 2. external apis Required to be a spring bean
	 * as spring will call the postConstruct method to bootstrap swagger
	 * scanning.
	 * 
	 * @return
	 */
	@Bean
	public SwaggerApiResourceListing swaggerApiResourceListing() {
		// The group name is important and should match the group set on
		// ApiListingReferenceScanner
		// Note that swaggerCache() is by DefaultSwaggerController to serve the
		// swagger json
		SwaggerApiResourceListing swaggerApiResourceListing = new SwaggerApiResourceListing(
				springSwaggerConfig.swaggerCache(), RELATIVE_GROUP);

		// Set the required swagger settings
		swaggerApiResourceListing
				.setSwaggerGlobalSettings(swaggerGlobalSettings());

		// Use a custom path provider or
		// springSwaggerConfig.defaultSwaggerPathProvider()
		// swaggerApiResourceListing.setSwaggerPathProvider(demoPathProvider());
		swaggerApiResourceListing.setSwaggerPathProvider(relativeSwaggerPathProvider);

		// Supply the API Info as it should appear on swagger-ui web page
		swaggerApiResourceListing.setApiInfo(apiInfo());

		// Every SwaggerApiResourceListing needs an ApiListingReferenceScanner
		// to scan the spring request mappings
		swaggerApiResourceListing
				.setApiListingReferenceScanner(relativeApiListingReferenceScanner());
		return swaggerApiResourceListing;
	}

	@Bean
	public ApiListingReferenceScanner relativeApiListingReferenceScanner() {
		ApiListingReferenceScanner apiListingReferenceScanner = new ApiListingReferenceScanner();
		apiListingReferenceScanner
				.setRequestMappingHandlerMapping(springSwaggerConfig
						.swaggerRequestMappingHandlerMappings());
		apiListingReferenceScanner.setExcludeAnnotations(springSwaggerConfig
				.defaultExcludeAnnotations());
		apiListingReferenceScanner
				.setResourceGroupingStrategy(springSwaggerConfig
						.defaultResourceGroupingStrategy());
		apiListingReferenceScanner.setSwaggerPathProvider(relativeSwaggerPathProvider);
		apiListingReferenceScanner.setSwaggerGroup(RELATIVE_GROUP);
		return apiListingReferenceScanner;
	}
	
	@Bean
	public SwaggerPathProvider relativeSwaggerPathProvider() {
		return new NickNackSwaggerPathProvider("http://nicknack.herokuapp.com");
	}

	public static class NickNackSwaggerPathProvider extends DefaultSwaggerPathProvider {

		private final String appBasePath;

		public NickNackSwaggerPathProvider(String appBasePath) {
			this.appBasePath = appBasePath;
		}

		@Override
		public String getAppBasePath() {
			return appBasePath;
		}

		@Override
		public String getApiResourcePrefix() {
			return "/api";
		}
	}

}