package com.eshcherbinina.generalstore;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@SpringBootTest
@Profile("test")
@PropertySource(value="classpath:application-test.properties")
class GeneralStoreApplicationTests {

	@Test
	void contextLoads() {
	}
}
