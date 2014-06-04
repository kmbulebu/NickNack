package com.oakcity.nicknack.server.it;

import static com.jayway.restassured.RestAssured.*;
import static com.jayway.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import org.junit.Test;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.LinkDiscoverer;
import org.springframework.hateoas.hal.HalLinkDiscoverer;

import com.jayway.restassured.response.Response;
import com.oakcity.nicknack.server.model.PlanResource;

public class _EventFilterIT extends _AbstractJettyTest {
	
	final LinkDiscoverer linkDiscoverer = new HalLinkDiscoverer();

	
	@Test
	public void createPlanFilterForExampleProvider() {
		// Create a new Plan
		final String planJson = given().contentType("application/json").body("{\"name\":\"My Plan\"}").post("/api/plans").then().assertThat().statusCode(201).extract().asString();
		System.out.println(planJson);
		// New plan has links to self, eventFilters, and actions
		Link planLink = linkDiscoverer.findLinkWithRel("self", planJson);
		Link eventFiltersLink = linkDiscoverer.findLinkWithRel("eventFilters", planJson);
		Link actionsLink = linkDiscoverer.findLinkWithRel("actions", planJson);
		
		// Create a new event filter for this plan.
		final String eventFilterJson = given().contentType("application/json").body("{\"appliesToEventDefinition\":\"320c68e0-d662-11e3-9c1a-0800200c9a66\"}").post(eventFiltersLink.getHref()).then().assertThat().statusCode(201).extract().asString();
		
		System.out.println(eventFilterJson);
		Link eventFilterLink = linkDiscoverer.findLinkWithRel("self", eventFilterJson);
		
		//  Check that it exists.
		get(eventFiltersLink.getHref()).then().assertThat().statusCode(200).body("_embedded.EventFilters", hasSize(1));
	}
	
	

}
