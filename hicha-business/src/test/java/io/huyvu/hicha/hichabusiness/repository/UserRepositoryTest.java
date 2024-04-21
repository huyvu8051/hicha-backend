package io.huyvu.hicha.hichabusiness.repository;

import io.huyvu.hicha.hichabusiness.model.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {
    @Container
    @ServiceConnection
    static MariaDBContainer<?> mariadb = new MariaDBContainer<>("mariadb:latest")
            .withDatabaseName("hicha")
            .withUsername("myuser")
            .withPassword("secret");


    @Autowired
    private UserRepository userRepository;

    @Test
    void connectionEstablished() {
        assertThat(mariadb.isCreated()).isTrue();
        assertThat(mariadb.isRunning()).isTrue();
    }

    /*@DynamicPropertySource
    static void neo4jProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url",mariadb::getJdbcUrl);
        registry.add("spring.datasource.username", mariadb::getUsername);
        registry.add("spring.datasource.password", mariadb::getPassword);
    }*/
    @BeforeEach
    void setup() {
        List<UserDTO> userDTOS = List.of(new UserDTO(4L, "Son Tung M-TP"), new UserDTO(2L, "Hieu Thu Hai"), new UserDTO(3L, "Truc Nhan"));
        for (UserDTO userDTO : userDTOS) {
            userRepository.save(userDTO);
        }
    }


    @Test
    void shouldReturnAllUser() {
        List<UserDTO> all = userRepository.findAll();
        assertThat(all).isNotNull().isNotEmpty();
    }


}