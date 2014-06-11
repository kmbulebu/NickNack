package com.oakcity.nicknack.server.it;

import static com.jayway.restassured.RestAssured.*;
import static com.jayway.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import org.junit.Before;
import org.junit.Test;

import com.jayway.restassured.RestAssured;

public abstract class _AbstractJettyTest {
	
	protected int httpPort;
	
	public _AbstractJettyTest() {
		final String httpPort = System.getProperty("http.port");
		
		if (httpPort == null || !httpPort.matches("\\d+")) {
			throw new IllegalArgumentException("http.port system property must be set to the http server port number.");
		}
		
		this.httpPort = Integer.parseInt(httpPort);
	}
	
	@Before
	public void setupRestAssured() {
		RestAssured.port = this.httpPort;
		RestAssured.responseContentType("application/hal+json");
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
	}
	
	@Test
	public void basicConnectivityTest() {
		RestAssured.responseContentType("text/html");
		get("/").then().assertThat().statusCode(equalTo(200));
	}

}
