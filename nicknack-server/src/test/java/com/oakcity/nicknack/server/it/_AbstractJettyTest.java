package com.oakcity.nicknack.server.it;

import static com.jayway.restassured.RestAssured.get;
import static org.hamcrest.Matchers.equalTo;

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
		
		RestAssured.port = this.httpPort;
	}
	
	@Test
	public void basicConnectivityTest() {
		get("/").then().assertThat().statusCode(equalTo(200));
	}

}
