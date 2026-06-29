package com.fitness.activityService;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
@SpringBootTest(properties = {
		"eureka.client.enabled=false"
})
class ActivityServiceApplicationTests {

	@Test
	void contextLoads() {
	}

}
