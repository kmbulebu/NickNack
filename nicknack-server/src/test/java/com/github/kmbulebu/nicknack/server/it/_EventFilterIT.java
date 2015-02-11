package com.github.kmbulebu.nicknack.server.it;

import static com.jayway.restassured.RestAssured.*;
import static com.jayway.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.LinkDiscoverer;
import org.springframework.hateoas.hal.HalLinkDiscoverer;

import com.github.kmbulebu.nicknack.server.model.PlanResource;
import com.jayway.restassured.response.Response;

public class _EventFilterIT extends _AbstractJettyTest {
	
	final LinkDiscoverer linkDiscoverer = new HalLinkDiscoverer();

	
	@Test
	@Ignore
	public void createPlanFilterForExampleProvider() {
		// Create a new Plan
		final String planJson = given().contentType("application/json").body("{\"name\":\"My Plan\"}").post("/api/plans").then().assertThat().statusCode(201).extract().asString();
		System.out.println(planJson);
		// New plan has links to self, eventFilters, and actions
		Link planLink = linkDiscoverer.findLinkWithRel("self", planJson);
		Link eventFiltersLink = linkDiscoverer.findLinkWithRel("EventFilters", planJson);
		Link actionsLink = linkDiscoverer.findLinkWithRel("Actions", planJson);
		
		// Create a new event filter for this plan.
		final String eventFilterJson = given().contentType("application/json").body("{\"appliesToEventDefinition\":\"320c68e0-d662-11e3-9c1a-0800200c9a66\"}").post(eventFiltersLink.getHref()).then().assertThat().statusCode(201).extract().asString();
		
		//System.out.println(eventFilterJson);
		Link eventFilterLink = linkDiscoverer.findLinkWithRel("self", eventFilterJson);
		
		//  Check that it exists.
		get(eventFiltersLink.getHref()).then().assertThat().statusCode(200).body("_embedded.EventFilters", hasSize(1));
		
		// Create some attribute filters to check if a particular switch is switched to on
		// TODO Move up to event filter
	//	final String macAttributeFilterJson = given().contentType("application/json").body("{\"appliesToAttributeDefinition\":\"920c68e0-d662-31e3-9c1a-0800200d9a66\",\"operator\":\"EQUALS\",\"operand\":\"0b:41:c1:3a:89:36\"}").post(attributeFiltersLink.getHref()).then().assertThat().statusCode(201).extract().asString();
	// final String positionAttributeFilterJson = given().contentType("application/json").body("{\"appliesToAttributeDefinition\":\"320c68e0-d662-11e3-9c1a-0800200d9a66\",\"operator\":\"EQUALS\",\"operand\":\"true\"}").post(attributeFiltersLink.getHref()).then().assertThat().statusCode(201).extract().asString();
	}
	
	

}
