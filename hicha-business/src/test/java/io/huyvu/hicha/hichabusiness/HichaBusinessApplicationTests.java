package io.huyvu.hicha.hichabusiness;

import io.huyvu.hicha.hichabusiness.model.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.CassandraContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/*
 * Integration Test
 */

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
class HichaBusinessApplicationTests {

	@Container
	@ServiceConnection
	static CassandraContainer<?> cassandraDatabase = new CassandraContainer<>("cassandra:4.0.12");


	@Autowired
	TestRestTemplate restTemplate;

	@Test
	void returnAtLeaseOne(){
		var response = restTemplate.getForObject("/api/v1/user", List.class);
		assertThat(response.size()).isGreaterThan(0);
	}

	@Test
	void saveOneUser(){

		var result = restTemplate.postForObject("/api/v1/user", new UserDTO(null, "Jack Ma"), String.class);
		assertThat(result).isEqualTo("Success");

		var response = restTemplate.getForObject("/api/v1/user", List.class);
		assertThat(response.size()).isGreaterThan(1);
	}

	@Test
	void findByIdReturnOne(){
		UserDTO forObject = restTemplate.withBasicAuth("huyvu", "password").getForObject("/api/v1/user/1", UserDTO.class);
		log.info(String.valueOf(forObject));
		assertThat(forObject).isNotNull();
	}

	@Test
	void findByIdReturnNull(){
		UserDTO forObject = restTemplate.getForObject("/api/v1/user/-1", UserDTO.class);
		assertThat(forObject).isNull();
	}

}
