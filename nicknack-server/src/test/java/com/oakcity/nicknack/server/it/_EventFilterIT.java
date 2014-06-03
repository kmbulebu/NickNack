package com.oakcity.nicknack.server.it;

import static com.jayway.restassured.RestAssured.*;
import static com.jayway.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import org.junit.Test;

public class _EventFilterIT extends _AbstractJettyTest {
	
	@Test
	public void createEventFilterForExampleProvider() {
		given().contentType("application/json").body("{\"name\":\"My Plan\"}").post("/api/plans").then().assertThat().statusCode(200);
	}
	
	

}
