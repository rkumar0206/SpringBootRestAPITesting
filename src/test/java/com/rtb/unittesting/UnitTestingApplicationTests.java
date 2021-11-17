package com.rtb.unittesting;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


/*
 * For adding your custom configuration, we can provide a location of the 
 * configuration.properties file in the @TestPropertySource
 */
//@TestPropertySource(locations = {"classpath:test-configuration.properties"})
@SpringBootTest
class UnitTestingApplicationTests {

	@Test
	void contextLoads() {
	}

}
