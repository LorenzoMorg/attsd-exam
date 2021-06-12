package com.examples.spring.project;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ActiveProfiles("mysql")
@SpringBootTest
class AttsdSpringProjectApplicationTests {

	@Test
	void contextLoads() {
	}

}
