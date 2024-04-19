package io.huyvu.hicha.hichabusiness;

import io.huyvu.hicha.hichabusiness.model.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

/*
 * Integration Test
 */
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
class HichaBusinessApplicationTests {

	@Container
	@ServiceConnection
	static MariaDBContainer<?> mariaDBContainer =  new MariaDBContainer<>("mariadb:latest")
			.withDatabaseName("hicha")
			.withUsername("myuser")
			.withPassword("secret");

	@Autowired
	TestRestTemplate restTemplate;



	@Test
	void returnHelloWorld(){
		String response = restTemplate.getForObject("/api/v1", String.class);
		log.info(response);
		Assertions.assertThat(response).isNotEmpty();
	}

}
