package com.rtb.unittesting.controller;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;

/*
 * It searches for the annotation @SpringBootApplication package by package
 * and after finding it, it launches the entire app, means the components, 
 * services, repositories, in memory database etc.
 * 
 * RANDOM_PORT :  spring boot will choose a random port to 
 * run our web application
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ItemControllerIntegrationTest {

	@Autowired
	private TestRestTemplate restTemplate;
	
	@Test
	public void contextLoads() throws JSONException {
		
		String response =  this.restTemplate.getForObject("/all-items-from-database", String.class);
		
		JSONAssert.assertEquals("[{id:1001}, {id:1002}, {id:1003}]", response, false);
	}
}
