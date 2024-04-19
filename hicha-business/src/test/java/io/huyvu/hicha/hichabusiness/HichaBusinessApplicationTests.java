package io.huyvu.hicha.hichabusiness;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

/*
 * Integration Test
 */
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HichaBusinessApplicationTests {

	/*@Test
	void contextLoads() {
	}*/

	@Container
	@ServiceConnection
	static MariaDBContainer<?> mariaDBContainer = new MariaDBContainer<>("mariadb:latest");

	@Autowired
	TestRestTemplate restTemplate;

	@Test
	void returnHelloWorld(){
		String response = restTemplate.getForObject("/api/v1", String.class);
		Assertions.assertThat(response).isEqualTo("Hello World!");
	}

}
