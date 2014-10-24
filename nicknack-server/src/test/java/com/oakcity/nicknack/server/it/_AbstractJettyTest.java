package com.oakcity.nicknack.server.it;

import static com.jayway.restassured.RestAssured.get;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.github.kmbulebu.nicknack.server.Application;
import com.jayway.restassured.RestAssured;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest({"server.port=0", "management.port=0", "nicknack.user.config=./target/config/nicknack_config.xml", "nicknack.db.path=./target/db/nicknack"})
public abstract class _AbstractJettyTest {
	
	@Value("${local.server.port}")
    int port;
	
	@Before
	public void setupRestAssured() {
		RestAssured.port = this.port;
		RestAssured.responseContentType("application/hal+json");
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
	}
	
	@Test
	public void basicConnectivityTest() {
		get("/api").then().assertThat().statusCode(equalTo(200));
	}

}
