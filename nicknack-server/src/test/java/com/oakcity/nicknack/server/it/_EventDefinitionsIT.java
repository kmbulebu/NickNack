package com.oakcity.nicknack.server.it;

import static com.jayway.restassured.RestAssured.*;
import static com.jayway.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import org.junit.Test;

public class _EventDefinitionsIT extends _AbstractJettyTest {
	
	@Test
	public void testExampleProvider() {
		System.out.println(get("/api/eventDefinitions").asString());
		get("/api/eventDefinitions").then().assertThat().body("any { it.key == 'content' }", is(true));
		get("/api/eventDefinitions").then().assertThat().body("content.any { it.containsKey('uuid') }", is(true));
		get("/api/eventDefinitions").then().assertThat().body("content.any { it.containsKey('name') }", is(true));
		// get("/api/eventDefinitions").then().assertThat().body("content.links.any { it. }", is(true));
		
		// TODO Test stuff
	}
	
	
	
	
	

}
